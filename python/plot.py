import os
import sys
from datetime import datetime
import pandas as pd
import matplotlib.pyplot as plt


def pd_read_csv(path):
    headers = ['DateTime', 'Temperatur']
    df = pd.read_csv(path, names=headers)
    return df


def pd_write_csv(path, df):
    directory = path[:-11]
    df.to_csv(directory + "data.csv", index=False, header=False)


def pd_drop(df, index):
    df = df.drop(df.index[index])
    return df


def pd_generate_t0(index, df):
    df['DateTimeTemp'] = df['DateTime'].map(lambda x: datetime.strptime(str(x), '%Y-%m-%d_%H-%M-%S'))
    df['delta'] = df['DateTimeTemp'] - df['DateTimeTemp'].iloc[index]
    df['total_seconds'] = df['delta'].dt.total_seconds()
    df = df.drop(columns=['delta'])
    df = df.drop(columns=['DateTimeTemp'])
    return df


def pd_hottest_point(df):
    return df['Temperatur'].idxmax('columns', skipna=True)


def plot(df, path):
    directory = path[:-11]
    # df['DateTime'] = df['DateTime'].map(lambda x: datetime.strptime(str(x), '%Y-%m-%d_%H-%M-%S'))
    y = df['Temperatur']
    x = df['total_seconds']
    # plot
    fig = plt.figure()
    ax = plt.subplot(111)
    ax.plot(x, y, label='$y = numbers')
    fig.savefig(directory + 'plot.png')
    # plt.show()


# takes results.csv and plots the data and converts to data.csv
csvpath = sys.argv[1]
# csvpath = "/Volumes/DiePlatte/uni/WS18_19/DropBoxTeamordner/ThermalImageGUI/thermalImageProjects/check/15-01-19_14:43/results.csv"
df = pd_read_csv(csvpath)
index = 1  # second element is the first of the measurement
df = pd_generate_t0(index, df)
os.remove(csvpath)

directory = csvpath[:-11]
# df['DateTime'] = df['DateTime'].map(lambd
y = df['Temperatur']
x = df['total_seconds']
# plot
fig = plt.figure()
axes = plt.axes()
plt.plot(x, y, label="Abkühlung", color="orange")
plt.ylabel("Temperatur in [°C]")
plt.xlabel("Zeit in [Sekunden]")
plt.legend()

fig.savefig(directory + 'plot.png')

pd_write_csv(csvpath, df)
