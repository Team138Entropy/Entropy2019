/*
  Code for reading an array of 8 I2C RGB color sensors (Adafruit 1334) that
  are connected to an I2C expander (Adafruit 2717)

  Sensors are initialized and calibrated in setup()

  Sensor values are read continuously in loop().

  Sensor values are mapped to output values of 0-255 and constrained to this range.

  Jan. 23, 2018
  
 */

#include "Wire.h"
// extern "C" { 
// #include "utility/twi.h"  // from Wire library, so we can do bus scanning
// }
#include "Adafruit_TCS34725.h"

#define TCAADDR 0x70

void tcaselect(uint8_t i) {
  if (i > 7) return;
 
  Wire.beginTransmission(TCAADDR);
  Wire.write(1 << i);
  Wire.endTransmission();  
}

uint16_t r[8];
uint16_t g[8];
uint16_t b[8];
uint16_t c[8];

int rmin[] = {10000,10000,10000,10000,10000,10000,10000,10000};
int rmax[] = {0, 0, 0, 0, 0, 0, 0, 0};

int sensor[8];



Adafruit_TCS34725 rgb0 = Adafruit_TCS34725(TCS34725_INTEGRATIONTIME_5MS, TCS34725_GAIN_4X);
Adafruit_TCS34725 rgb1 = Adafruit_TCS34725(TCS34725_INTEGRATIONTIME_5MS, TCS34725_GAIN_4X);
Adafruit_TCS34725 rgb2 = Adafruit_TCS34725(TCS34725_INTEGRATIONTIME_5MS, TCS34725_GAIN_4X);
Adafruit_TCS34725 rgb3 = Adafruit_TCS34725(TCS34725_INTEGRATIONTIME_5MS, TCS34725_GAIN_4X);
Adafruit_TCS34725 rgb4 = Adafruit_TCS34725(TCS34725_INTEGRATIONTIME_5MS, TCS34725_GAIN_4X);
Adafruit_TCS34725 rgb5 = Adafruit_TCS34725(TCS34725_INTEGRATIONTIME_5MS, TCS34725_GAIN_4X);
Adafruit_TCS34725 rgb6 = Adafruit_TCS34725(TCS34725_INTEGRATIONTIME_5MS, TCS34725_GAIN_4X);
Adafruit_TCS34725 rgb7 = Adafruit_TCS34725(TCS34725_INTEGRATIONTIME_5MS, TCS34725_GAIN_4X);


// standard Arduino setup()
void setup()
{
  pinMode(2, INPUT_PULLUP);
  pinMode(7,OUTPUT);
  digitalWrite(7,HIGH);
  pinMode(8,OUTPUT);
  digitalWrite(8,HIGH);
  
  while (!Serial);
  delay(1000);
  Wire.begin();
  Serial.begin(115200);

// Initialise sensors
  tcaselect(0);
  rgb0.begin();
  tcaselect(1);
  rgb1.begin();
  tcaselect(2);
  rgb2.begin();
  tcaselect(3);
  rgb3.begin();
  tcaselect(4);
  rgb4.begin();
  tcaselect(5);
  rgb5.begin();
  tcaselect(6);
  rgb6.begin();
  tcaselect(7);
  rgb7.begin();
  
  while(digitalRead(2)== 1){
    tcaselect(0);
    rgb0.getRawData(&r[0], &g[0], &b[0], &c[0]);
    tcaselect(1);
    rgb1.getRawData(&r[1], &g[1], &b[1], &c[1]);
    tcaselect(2);
    rgb2.getRawData(&r[2], &g[2], &b[2], &c[2]);
    tcaselect(3);
    rgb3.getRawData(&r[3], &g[3], &b[3], &c[3]);
    tcaselect(4);
    rgb4.getRawData(&r[4], &g[4], &b[4], &c[4]);
    tcaselect(5);
    rgb5.getRawData(&r[5], &g[5], &b[5], &c[5]);
    tcaselect(6);
    rgb6.getRawData(&r[6], &g[6], &b[6], &c[6]);
    tcaselect(7);
    rgb7.getRawData(&r[7], &g[7], &b[7], &c[7]);

    for (int i=0; i<8; i++)
    {
    sensor[i] = r[i]+g[i]+b[i];
    if (sensor[i] > rmax[i]){
      rmax[i] = sensor[i];
      }
    if (sensor[i] < rmin[i]){
      rmin[i] = sensor[i];
      }
    Serial.print(rmin[i]);Serial.print("  ");  
    Serial.print(rmax[i]);Serial.print("  ");    
   }
   Serial.println("  ");   
  }
}     


void loop() 
{    
    tcaselect(0);
    rgb0.getRawData(&r[0], &g[0], &b[0], &c[0]);
    tcaselect(1);
    rgb1.getRawData(&r[1], &g[1], &b[1], &c[1]);
    tcaselect(2);
    rgb2.getRawData(&r[2], &g[2], &b[2], &c[2]);
    tcaselect(3);
    rgb3.getRawData(&r[3], &g[3], &b[3], &c[3]);
    tcaselect(4);
    rgb4.getRawData(&r[4], &g[4], &b[4], &c[4]);
    tcaselect(5);
    rgb5.getRawData(&r[5], &g[5], &b[5], &c[5]);
    tcaselect(6);
    rgb6.getRawData(&r[6], &g[6], &b[6], &c[6]);
    tcaselect(7);
    rgb7.getRawData(&r[7], &g[7], &b[7], &c[7]);
    
    for (int i=0; i<8; i++){
      sensor[i] = r[i]+g[i]+b[i];
      sensor[i] = map(sensor[i],rmin[i],rmax[i],0,255);
      sensor[i] = constrain(sensor[i],0,255);
      Serial.print(sensor[i]);Serial.print(" ");
      if(i==3){
        Serial.print("... ");
      }
    }
    Serial.println("  ");
      
    
}
