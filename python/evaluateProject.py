import collections
import sys
import os
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import numpy.polynomial.polynomial as polyNew


def pd_read_csv(path):
    headers = ['name', 'temp', 't0']
    df = pd.read_csv(path, names=headers)
    return df.drop(columns='name')


def highest_temp(df):
    temp = df['temp'].loc[df['temp'].idxmax()]
    return temp


def merge_dataframe(dfold, dfnew):
    a = 0
    return a


path = sys.argv[1]
# path = "/Volumes/DiePlatte/uni/WS18_19/DropBoxTeamordner/ThermalImageGUI/thermalImageProjects/Abdeckung"
projectname = path.split("/")
paths = []
csv_adder = dict()

# list all the experiments of the given Project and collect each data.csv
for experiments in os.listdir(path):
    if experiments != "background.png" and experiments != ".DS_Store" and experiments != "plot.png":
        # print(experiments)
        paths.append(path + "/" + experiments + "/data.csv")

for csv in paths:
    df = pd_read_csv(csv)
    while df['t0'][0] < 0.0:
        df = df.drop([0]).reset_index(drop=True)
    df = df.round({'temp': 1})
    csv_adder[highest_temp(df)] = df
    # if t0 is not on the second place correct it!

# assembling the csv data to one dataframe
df = pd.DataFrame
csv_dict = collections.OrderedDict(sorted(csv_adder.items(), reverse=True))  # order the dict in descending order
for key in csv_dict.keys():
    if df.empty:  #
        df = csv_dict.get(key)
        df_temp = key
        print(df)
        print(len(df))
    else:
        next_df = csv_dict.get(key)
        df.append(next_df).reset_index(drop=True)

        i = 0
        while df['temp'][i] > key:
            i = i + 1
            if i >= len(df) - 1:
                # TODO this method does not work if this place is reached

                break
        # i marks the tempe in the bigger dataframe
        # print(df['temp'][i - 1], ">", key, ">", df['temp'][i])
        full_diff = np.absolute(df['temp'][i - 1] - np.absolute(df['temp'][i]))  # difference between inserting temps
        insert_diff = np.absolute(df['temp'][i] - key)
        percentage = insert_diff / full_diff
        time_diff = np.absolute(df['t0'][i - 1] - df['t0'][i])
        insert_time = df['t0'][i] - (time_diff * percentage / 100)
        # print(df['t0'][i - 1], insert_time, df['t0'][i])
        # print(percentage, "%")
        next_df['t0'] = next_df['t0'] + insert_time
        df = df.append(next_df).reset_index(drop=True)
        # print(next_df)
df = df.sort_values('t0').reset_index(drop=True)
print(df)

# plotting dataframe
fig = plt.figure()
axes = plt.axes()
x_values = df['t0']

coef = polyNew.polyfit(x_values, df['temp'], 4)
y_values = polyNew.polyval(x_values, coef)

plt.title(projectname[-1])
plt.xlabel("Zeit in [Sekunden]")
plt.ylabel("Temperatur in [°C]")

orig = plt.plot(x_values, df['temp'], color='blue', marker='o'
                , linestyle='dashed', linewidth=1, markersize=4, alpha=0.4, label="gemessene Werte")
poly = plt.plot(x_values, y_values, 'r', label="gemittelte Werte")
plt.legend()
# show grid
axes.grid(alpha=0.3)
# number of ticks on y axis
plt.locator_params(axis='y', nbins=12)
plt.show()
fig.savefig(path + "/plot.png")
