import cv2
import csv
import sys
import os
from datetime import datetime
import pandas as pd
import matplotlib.pyplot as plt


def get_hottest_point_robust(img, radius):
    # radius must be an odd number
    blur = cv2.GaussianBlur(img, (radius, radius), 0)
    (minVal, maxVal, minLoc, maxLoc) = cv2.minMaxLoc(blur)
    return maxLoc


def read_masked_img(path, mask):
    img = cv2.imread(path, cv2.IMREAD_GRAYSCALE)
    cv2.normalize(mask, mask, 0, 1, cv2.NORM_MINMAX)
    maskedimg = cv2.multiply(img, mask)
    return maskedimg


def convert_to_celsius(x):
    max = 37
    min = 11.4
    a = min
    b = ((max - min) / 255)
    # b = (1 / 17)
    return (a + (b * x))


def background_substraction(imgPath):
    # accepts path for thermal image
    backgroundPath = imgPath[:-56] + "background.png"
    background = cv2.imread(backgroundPath)
    orig = imgPath[:-4] + "-orig.png"
    img = cv2.imread(orig)
    background = cv2.medianBlur(background, 5)
    img = cv2.medianBlur(img, 5)
    difference = cv2.absdiff(background, img)
    blur = cv2.medianBlur(difference, 15)
    gray = cv2.cvtColor(blur, cv2.COLOR_BGR2GRAY)
    retval, mask = cv2.threshold(gray, 30, 255, cv2.THRESH_BINARY)
    thermal = cv2.imread(imgPath)
    height, width = thermal.shape[:2]
    ret = cv2.resize(mask, (width, height), interpolation=cv2.INTER_CUBIC)
    return ret


def draw_circle(img, point):
    # (minVal, maxVal, minLoc, maxLoc) = cv2.minMaxLoc(img)
    color = cv2.cvtColor(img, cv2.COLOR_GRAY2BGR)
    cv2.circle(color, center=point, radius=20, color=(0, 0, 255), thickness=3)
    return color


def img_write(absolute_path, img):
    name = absolute_path[:-41] + absolute_path[-28:-9] + ".png"
    cv2.imwrite(name, img, [cv2.IMWRITE_PNG_COMPRESSION, 9])


def print_csv(thermalimgpath, temp, maxLoc):
    directory = thermalimgpath[:-41]
    date = thermalimgpath[-28:-9]
    with open(directory + "results.csv", 'a', newline='') as csvfile:
        test_writer = csv.writer(csvfile, delimiter=',', quotechar='|', quoting=csv.QUOTE_MINIMAL)
        test_writer.writerow([date] + [temp])


def neighbourhood(img, point):
    x = point[1]
    y = point[0]
    temp = 0
    summe = 0
    for i in range(-2, 3):
        for j in range(-2, 3):
            pixel = (x + i, y + j)
            temp = temp + img[pixel]
            summe = summe + 1
    return temp / summe


path = sys.argv[1]
print(path)
mask = background_substraction(path)
masked_img = read_masked_img(path, mask)
point = get_hottest_point_robust(masked_img, 41)
neighbours = neighbourhood(cv2.imread(path, cv2.IMREAD_GRAYSCALE), point)
# temp = convert_to_celsius(masked_img[point[1], point[0]])
temp = convert_to_celsius(neighbours)
control_img = draw_circle(masked_img, point)
print("remove thermal")
os.remove(path)
print("remove orig")
os.remove(path[:-4] + "-orig.png")
print("imwrite checkimg")
img_write(path, control_img)
print("print csv")
print_csv(path, temp, point)

