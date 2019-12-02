from operator import concat
import numpy as np
import pandas as pd
import torch
import torch.nn as nn
import matplotlib.pyplot as plt

from pandas import DataFrame, read_csv
from sklearn.preprocessing import MinMaxScaler, LabelEncoder

# load dataset
from torch.optim.lr_scheduler import _LRScheduler
from torch.utils.data import DataLoader, Dataset

totalSize = 1000000
#75/25 Train test split
trainSize = int((totalSize / 4) * 3)


batchSize = 1

dataset = pd.read_csv("dataForCianDaily.csv")

dataset = dataset[1:totalSize]
dataset = dataset.drop(dataset.columns[[0]], axis=1)
values = dataset.values

# Split into training/test
values = values.astype('float32')
train_data = values[:trainSize]
test_data = values[trainSize:]

# normalize features
scalar = MinMaxScaler(feature_range=(0, 1))
scaled_train = scalar.fit_transform(train_data)
scaled_test = scalar.transform(test_data)

# Turn into Tensorflow Tensors
train_data_normalized = torch.FloatTensor(scaled_train).contiguous().view(-1)
test_data_normalized = torch.FloatTensor(scaled_test).contiguous().view(-1)

# 361 = the column size - this will split data into tensors tuples of input + output tensors
train_window = 6


# Convert to tensor tuples
def input_series_sequence(input_data, tw):
    inout_seq = []
    L = len(input_data)
    i = 0
    for index in range(L - tw):
        train_seq = input_data[i:i + tw]
        train_label = input_data[i + tw:i + tw + 1]
        inout_seq.append((train_seq, train_label))
        if (i + tw) < L:
            i = i + tw + 1
        else:
            break
    return inout_seq


train_inout_seq = input_series_sequence(train_data_normalized, train_window)
test_input_seq = input_series_sequence(test_data_normalized, train_window)

# Seems to be  couple empty tensors at the end of these arrays so knocking off a couple entries at the end
train_inout_seq = train_inout_seq[:trainSize - 5]
test_input_seq = test_input_seq[:totalSize - (trainSize + 5)]



#PyTorch Dataset class, allows batch size modification + Shuffling
class oversampdata(Dataset):
    def __init__(self, data):
        self.data = data

    def __len__(self):
        return len(self.data)

    def __getitem__(self, index):
        target = self.data[index][-1]
        data_val = self.data[index][:-1]

        return data_val[0], target


train_dataset = oversampdata(train_inout_seq)
valid_dataset = oversampdata(test_input_seq)

#Do not shuffle data as its sequential time - series
train_loader = DataLoader(train_dataset, batch_size=batchSize, shuffle=False, drop_last=True)
test_loader = DataLoader(valid_dataset, batch_size=batchSize, shuffle=False, drop_last=True)



# Neural network class
class LSTM(nn.Module):
    def __init__(self, input_size=6, hidden_layer_size=50, output_size=batchSize):
        super().__init__()
        self.hidden_layer_size = hidden_layer_size

        self.lstm = nn.LSTM(input_size, hidden_layer_size, num_layers=2, dropout=0.4)

        self.linear = nn.Linear(hidden_layer_size, output_size)

        self.hidden_cell = (torch.zeros(1, 1, self.hidden_layer_size),
                            torch.zeros(1, 1, self.hidden_layer_size))

    def init_hidden(self):
	    #Clear the hidden state - append .cuda() if running on GPU
        return (torch.zeros(2, 1, self.hidden_layer_size),
                torch.zeros(2, 1, self.hidden_layer_size))

    def forward(self, input_seq):
		# Propogate inputs through the network
        lstm_out, self.hidden_cell = self.lstm(input_seq.view(len(input_seq), 1, -1), self.hidden_cell)
        predict = self.linear(lstm_out.view(len(input_seq), -1))
        return predict[-1]


# Set device = GPU list if its availible
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

# Create model
model = LSTM()
loss_function = nn.MSELoss()
lr = 0.0001
iterations_per_epoch = len(train_inout_seq)
optimizer = torch.optim.Adam(model.parameters(), lr=lr)


# Transfer the model + Train test data to the device
model.to(device)

epochs = 20

trainPredictions = []
trainActual = []
# Training time

trainLoss = []
testLoss = []
testPredictions = []
best = 0
realValues = []
for i in range(epochs):
    loss = 0
    model.train()
    for seq, labels in train_loader:
		# Send inputs + labels to the device
        seq = seq.to(device)
        labels = labels.to(device)
        labels = labels.view(-1)
		
		# Zero the gradient and initialize hidden state
        optimizer.zero_grad()
        model.hidden_cell = model.init_hidden()

		# Predict and calculate loss
        y_pred = model(seq)
        single_loss = loss_function(y_pred, labels)
   
		# Backpropogate
        single_loss.backward()
        loss = single_loss.item()
		#Clip the gradient, helps with LSTM's vanishing/exploding gradient problem
        nn.utils.clip_grad_norm_(model.parameters(), 5)
        optimizer.step()

     
            # Predicting time!
    model.eval()

    for seq, labels in test_loader:
        seq = seq.to(device)
        labels = labels.to(device)
        labels = labels.view(-1)

        with torch.no_grad():
            model.hidden_cell = model.init_hidden()

            temp = model(seq)
           
            tloss = loss_function(temp, labels)
            tloss = tloss.item()

            

    trainLoss.append(loss)
    testLoss.append(tloss)


    if testScore > best:
        torch.save(model.state_dict(), "best.pth")

    print(
        "Epoch: %d, loss: %1.5f, val_loss: %1.5f, test acc: %1.5f , test diff:%1.5f  train acc: %1.5f, train diff: %1.5f  " % (
        i, loss, tloss, testScore, testdiff, trainScore, diff))

# Save the model
torch.save(model.state_dict(), "trainedmodel.pth")

# model.load_state_dict(torch.load("trainedmodel.pth"))

plt.title('Train loss v Test Loss(Orange)')
plt.grid(True)
plt.autoscale(axis='x', tight=True)
plt.plot(trainLoss)
plt.plot(testLoss)
plt.show()

plt.title('Predicted vs actual(Orange):Test')
plt.ylabel('Btc price')
plt.grid(True)
plt.autoscale(axis='x', tight=True)
plt.plot(testPredictions)
plt.plot(realValues)
plt.show()

plt.title('Predicted vs actual(Orange):Train')
plt.ylabel('Btc price')
plt.grid(True)
plt.autoscale(axis='x', tight=True)
plt.plot(trainPredictions)
plt.plot(trainActual)
plt.show()
