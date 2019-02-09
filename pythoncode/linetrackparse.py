from sys import argv
import json
import math


# the physical distance (in arbitrary units) between sensor rows
ROW_DISTANCE = 2
# the physical distance (in arbitrary units) between sensors in the same rows
SENSOR_DISTANCE = 1

# takes a 2d array of ints
# eg. [[1], [2, 3]]
# and flattens (averages) each sub-list
def flattenIdxs(idxs: list):
    averaged = []
    for row in idxs:
        averaged.append(sum(row) / len(row))
    return averaged

# converts the list to a degree measurement
def linetrackparse(sensors : list):
    print("#array of sensors", sensors)

    # the list of indexes of "True", 2d array of booleans
    idxs = []

    # the length of each row. used to check if each row is the same length
    rowLen = None
    for rowIdx in range(len(sensors)):
        idxs.append([])
        row = sensors[rowIdx]
        # if this is our first row, set it to the length
        if rowLen == None:
            rowLen = len(row)
        # otherwise see if the length is different than expected
        elif rowLen != len(row):
            raise ValueError("row length expected", rowLen, "but got", len(row), "in row #", rowIdx, "row", row)
        
        for i in range(len(row)):
            sensor = row[i]
            if sensor == True:
                # add this sensor index
                idxs[rowIdx].append(i)

        if len(idxs[rowIdx]) == 0:
            raise ValueError("expected each row to have at least one True, could not find any in row #",
                rowIdx, "row", row)
    
    # flatten (average) the list
    flatIdxs = flattenIdxs(idxs)
    print("#flattened into", flatIdxs)

    # avoid a division by zero error when the column of the sensors are the same
    if flatIdxs[1] - flatIdxs[0] == 0:
        deg = 90
    else:
        # calculate the angle
        deg = math.degrees(math.atan(
            ROW_DISTANCE / (SENSOR_DISTANCE * (flatIdxs[1] - flatIdxs[0]))
        ))
    print("#angle (degrees)")
    print(deg)
    return deg

if __name__ == "__main__":
    if len(argv) == 1 or argv[1] == "-h" or argv[1] == "--help":
        print("#Usage: linetrackparse.py '[[false, true, false, false], [false, false, false, true]]'")
        exit(1)

    data = json.loads(argv[1])
    linetrackparse(data)
