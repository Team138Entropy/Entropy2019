from sys import argv
import json
import math


# the physical distance (in arbitrary units) between sensor rows
ROW_DISTANCE = 2
# the physical distance (in arbitrary units) between sensors in the same rows
SENSOR_DISTANCE = 1

# math.degrees(math.atan())

# converts the list to a degree measurement
def linetrackparse(sensors : list):
    print(sensors)

    # the list of indexes of "True"
    idxs = []

    # the length of each row. used to check if each row is the same length
    rowLen = None
    for row in sensors:
        # if this is our first row, set it to the length
        if rowLen == None:
            rowLen = len(row)
        # otherwise see if the length is different than expected
        elif rowLen != len(row):
            raise ValueError("row length expected", rowLen, "to be", len(row))
        
        # number of "True"s in the row
        numTrues = 0
        for sensor in row:
            if sensor == True:
                numTrues += 1
        if numTrues != 1:
            raise ValueError("expected exactly 1 sensor per row to be true. found ", numTrues, "in", row)

        # add the index of true here
        idxs.append(row.index(True))

    # avoid a division by zero error when the column of the sensors are the same
    if idxs[1] - idxs[0] == 0:
        deg = 90
    else:
        # calculate the angle
        deg = math.degrees(math.atan(
            ROW_DISTANCE / (SENSOR_DISTANCE * (idxs[1] - idxs[0]))
        ))
    print(deg)
    return deg

if __name__ == "__main__":
    if len(argv) == 1 or argv[1] == "-h" or argv[1] == "--help":
        print("Usage: linetrackparse.py '[[false, true, false, false], [false, false, false, true]]'")
        exit(1)

    data = json.loads(argv[1])
    linetrackparse(data)
