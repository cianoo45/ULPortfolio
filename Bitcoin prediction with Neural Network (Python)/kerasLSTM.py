from operator import concat
import numpy as np
import pandas as pd
import torch
import torch.nn as nn
import matplotlib.pyplot as plt
import keras
from keras import Sequential
from keras.engine.saving import load_model
from keras.layers import LSTM, Dense, Bidirectional

from pandas import DataFrame, read_csv
from sklearn.preprocessing import MinMaxScaler, LabelEncoder

# load dataset

from torch.utils.data import DataLoader, Dataset

totalSize = 1000000
trainSize = int((totalSize / 4) * 3)
testSize = int((totalSize / 4))

batchSize = 2

dataset = pd.read_csv("final.csv")

dataset = dataset[1:totalSize]
dataset = dataset.drop(dataset.columns[[0]], axis=1)
dataset = dataset.dropna()
dataset.reset_index()


totalSize = len(dataset.index)
trainSize = int((totalSize / 4) * 3)




values = dataset.values

# Split into training/test
values = values.astype('float32')
train_data = values[:trainSize]
test_data = values[trainSize:]

# normalize features
scalar = MinMaxScaler(feature_range=(0, 1))

scaled_train = scalar.fit_transform(train_data)
scaled_test = scalar.transform(test_data)

# 361 = the column size - this will split data into tensors tuples of input + output tensors
train_window = 30


# split a multivariate sequence into samples
def split_sequences(sequences, n_steps):
    X, y = list(), list()
    for i in range(len(sequences)):
        # find the end of this pattern
        end_ix = i + n_steps
        # check if we are beyond the dataset
        if end_ix+2 > len(sequences):
            break
        # gather input and output parts of the pattern
        seq_x, seq_y = sequences[i:end_ix, :-1], sequences[end_ix +1, -1]
        X.append(seq_x)
        y.append(seq_y)
    return np.array(X), np.array(y)


X, y1 = split_sequences(scaled_train, train_window)
X1,y2 = split_sequences(scaled_test,train_window)
n_features = X.shape[2]



# Keras model, Relu activation is preffered over the standard sigmoid
model = Sequential()
model.add(LSTM(activation='relu',units=50,return_sequences=True,input_shape=(train_window,n_features)))
model.add(LSTM(activation='relu',units=50))
model.add(Dense(1))
model.compile(loss='mean_squared_error', optimizer='adam')

#Train the model
history = model.fit(X, y1, epochs=20, verbose=0)


#model = load_model('model.h5')

#Make predictions
yhat = model.predict(X1, verbose=0)


model.save("modelCian.h5")

# Plot training & validation loss values
plt.plot(history.history['loss'])
plt.title('Model loss')
plt.ylabel('Loss')
plt.xlabel('Epoch')
plt.legend(['Train'], loc='upper left')
plt.show()

plt.title('Predicted vs actual(Orange):Test')
plt.ylabel('Btc price')
plt.grid(True)
plt.autoscale(axis='x', tight=True)
plt.plot(yhat)
plt.plot(y2)
plt.show()


