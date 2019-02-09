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

# add the toggle button
button = digitalio.DigitalInOut(board.D4)
button.direction = digitalio.Direction.INPUT
button.pull = digitalio.Pull.UP

# detect changes
lastButtonValue = False

# a list of values recieved while in calibration mode
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

	# to account for button "bounce", delay .05 seconds and see if the button's state is the same
	# only continue if it is.
	value = not button.value
	time.sleep(0.05)
	oldValue = value
	value == (not button.value)
	if value == oldValue:
		# if the button's state was changed
		if not lastButtonValue == value:
			ticksSinceChange += 1
			# and it was changed for enough time
			if ticksSinceChange == maxTicks:
				toggled = True
				ticksSinceChange = 0

		# if the button was *really* pressed
		if toggled:
			toggled = False
			state += 0.5
			if state == 2:
				# sort, remove outliers from, and remove first & last from the whiteCalibrationValues
				whiteCalibrationValues.sort()
				whiteCalibrationValues = removeOutliers(whiteCalibrationValues).tolist()
				whiteCalibrationValues = whiteCalibrationValues[1:-1]
				#print("set whiteMin", whiteMin, "whiteMax", whiteMax)
			elif state == 3:
				# sort, remove outliers from, and remove first & last from the blackCalibrationValues
				blackCalibrationValues.sort()
				blackCalibrationValues = removeOutliers(blackCalibrationValues).tolist()
				blackCalibrationValues = blackCalibrationValues[1:-1]

				# find the middle between the extreme values
				threshold = (
					whiteCalibrationValues[0] + 
					blackCalibrationValues[-1]
				) / 2

			lastButtonValue = value

	stateVal = (state)

	if stateVal == 0:
		print("#press the button to calibrate white.")
	elif stateVal == 1:
		print("#calibrating white")
		whiteCalibrationValues.append(int(lux))
	elif stateVal == 2:
		print("#calibrating black")
		blackCalibrationValues.append(int(lux))
	elif stateVal > 2.5:
		print("#" + color("lux " + str(lux) + " threshold " + str(threshold),
			bg=("white" if lux > threshold else "black"),
			fg=("black" if lux > threshold else "white")
		))

	# show the state
	if stateVal < 3:
		print("#" + state)