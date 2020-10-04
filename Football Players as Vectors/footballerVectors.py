import math

import numpy as np
from sklearn.preprocessing import normalize
import matplotlib.pyplot as plt
from sklearn.decomposition import PCA
from numpy import dot
from numpy.linalg import norm

import pandas as pd
nameVectorTupleList=[]


playerDf = pd.read_csv('players_20.csv')
playerDetails = pd.read_csv('players_20_details.csv')
playerDetails = playerDetails.loc[playerDetails['team_position'] != "GK"]
playerDetails.to_csv('players_20_details.csv')


playerNames = playerDf['short_name']

vectorDf = playerDf.drop('short_name',1)


df_norm = (vectorDf - vectorDf.min()) / (vectorDf.max() - vectorDf.min())
df_norm=df_norm.fillna(0)
df_norm=df_norm.values

def cosine_similarity(v1,v2):
    "compute cosine similarity of v1 to v2: (v1 dot v2)/{||v1||*||v2||)"
    sumxx, sumxy, sumyy = 0, 0, 0
    for i in range(len(v1)):
        x = v1[i]; y = v2[i]
        sumxx += x*x
        sumyy += y*y
        sumxy += x*y
    return sumxy/math.sqrt(sumxx*sumyy)

# Calculate similarity between two players
def calculateSimilarity(player1,player2):
    norm1 = (df_norm[player1] - df_norm[player1].min()) / (df_norm[player1].max() - df_norm[player1].min())
    norm2= (df_norm[player2] - df_norm[player2].min()) / (df_norm[player2].max() - df_norm[player2].min())
    cos_sim =  cosine_similarity(norm1, norm2)
    # Cos_sim is edited here to make not related players seem further apart, has very little effect on the cos_sim of similiar players (Just an appearance thing)
    print(playerNames[player1], playerNames[player2], "Cos Similarity: ", (cos_sim-0.6)*2.5)


# Find the most similiar players to a given players
# "Player" parameter is an integer, look at players_20.csv to pick an index, then subtract 2. E.g : Van Djik is index 7 in csv, so input is 5
def mostSimiliarPlayers(player,topK,maxCheck=len(df_norm),maxAge=100,maxValue=100000000,minOverall=0,minPotential=0,nationality="Any"):
    listAll=[]
    playerNorm = (df_norm[player] - df_norm[player].min()) / (df_norm[player].max() - df_norm[player].min())
    for index,otherPlayer in enumerate(df_norm[0:maxCheck]):
        if not playerNames[index] == playerNames[player]:

            if  playerDetails['age'][index]<maxAge and  playerDetails['value_eur'][index] < maxValue and  playerDetails['overall'][index] > minOverall  and  playerDetails['potential'][index] > minPotential:
                if nationality=="Any":
                 otherPlayerNorm = (otherPlayer - otherPlayer.min()) / (otherPlayer.max() - otherPlayer.min())
                 cos_sim = cosine_similarity(playerNorm, otherPlayerNorm)
                 listAll.append([playerNames[index], cos_sim])
                elif playerDetails['nationality'][index] ==nationality:
                    otherPlayerNorm = (otherPlayer - otherPlayer.min()) / (otherPlayer.max() - otherPlayer.min())
                    cos_sim = cosine_similarity(playerNorm, otherPlayerNorm)
                    listAll.append([playerNames[index], cos_sim])


    print("Most similiar players to:",playerNames[player])
    print(sorted(listAll,key=lambda x: x[1],reverse=True)[0:topK])

mostSimiliarPlayers(20,topK=5,maxCheck=len(df_norm),minOverall=70,maxAge=22)













