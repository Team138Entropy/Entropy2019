# Simple demo of the TCS34725 color sensor.
# Will detect the color from the sensor and print it out every second.
import time
import numpy
from colors import *
import board
import busio
import digitalio
import adafruit_tcs34725

# 0 = not started
# 1 = calibrating white
# 2 = calibrating black
# 3 = run
# x.5 = button pressed & on state x
state = 0

# Initialize I2C bus and sensor.
i2c = busio.I2C(board.SCL, board.SDA)
sensor = adafruit_tcs34725.TCS34725(i2c)
sensor.integration_time = 50

button = digitalio.DigitalInOut(board.D4)
button.direction = digitalio.Direction.INPUT
button.pull = digitalio.Pull.UP

lastButtonValue = False
whiteCalibrationValues = []
blackCalibrationValues = []
threshold = 0

def removeOutliers(data, m=2.):
	data = numpy.array(data)
	return data[abs(data - numpy.mean(data)) < m * numpy.std(data)]

ticksSinceChange = 0
maxTicks = 2
toggled = False

# Main loop reading color and printing it every second.
while True:
	lux = sensor.lux

	value = not button.value
	if not lastButtonValue == value:
		ticksSinceChange += 1
		if ticksSinceChange == maxTicks:
			toggled = True
			ticksSinceChange = 0

	if toggled:
		toggled = False
		state += 0.5
		if state == 2:
			whiteCalibrationValues.sort()
			whiteCalibrationValues = removeOutliers(whiteCalibrationValues).tolist()
			whiteCalibrationValues = whiteCalibrationValues[1:-1]
			#print("set whiteMin", whiteMin, "whiteMax", whiteMax)
		elif state == 3:
			blackCalibrationValues.sort()
			blackCalibrationValues = removeOutliers(blackCalibrationValues).tolist()
			blackCalibrationValues = blackCalibrationValues[1:-1]

			threshold = (
				whiteCalibrationValues[0] + 
				blackCalibrationValues[-1]
			) / 2

		lastButtonValue = value

	stateVal = (state)

	if stateVal == 0:
		print("press the button to calibrate white.")
	elif stateVal == 1:
		print("calibrating white")
		whiteCalibrationValues.append(int(lux))
	elif stateVal == 2:
		print("calibrating black")
		blackCalibrationValues.append(int(lux))
	elif stateVal > 2.5:
		print(color("lux " + str(lux) + " threshold " + str(threshold),
			bg=("white" if lux > threshold else "black"),
			fg=("black" if lux > threshold else "white")
		))

	# show the state
	if stateVal < 3:
		print(state)