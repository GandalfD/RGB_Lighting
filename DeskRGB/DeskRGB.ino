#include <Adafruit_NeoPixel.h>
#ifdef __AVR__
  #include <avr/power.h>
#endif

#define PIN 6

// Parameter 1 = number of pixels in strip
// Parameter 2 = Arduino pin number (most are valid)
// Parameter 3 = pixel type flags, add together as needed:
//   NEO_KHZ800  800 KHz bitstream (most NeoPixel products w/WS2812 LEDs)
//   NEO_KHZ400  400 KHz (classic 'v1' (not v2) FLORA pixels, WS2811 drivers)
//   NEO_GRB     Pixels are wired for GRB bitstream (most NeoPixel products)
//   NEO_RGB     Pixels are wired for RGB bitstream (v1 FLORA pixels, not v2)
//   NEO_RGBW    Pixels are wired for RGBW bitstream (NeoPixel RGBW products)
Adafruit_NeoPixel strip = Adafruit_NeoPixel(60, PIN, NEO_GRB + NEO_KHZ800);

// IMPORTANT: To reduce NeoPixel burnout risk, add 1000 uF capacitor across
// pixel power leads, add 300 - 500 Ohm resistor on first pixel's data input
// and minimize distance between Arduino and first pixel.  Avoid connecting
// on a live circuit...if you must, connect GND first.

String incomingStr = "";

void setup() {
  Serial.begin(9600);
  strip.begin();
  strip.show(); // Initialize all pixels to 'off'

  colorWipe(strip.Color(29, 142 , 210), 30);
  delay(500);
  allOff();

  pinMode(13, OUTPUT);
}

void loop() {
  /*
  // Some example procedures showing how to display to the pixels:
  colorWipe(strip.Color(255, 0, 0), 50); // Red
  colorWipe(strip.Color(0, 255, 0), 50); // Green
  colorWipe(strip.Color(0, 0, 255), 50); // Blue
//colorWipe(strip.Color(0, 0, 0, 255), 50); // White RGBW
  // Send a theater pixel chase in...
  theaterChase(strip.Color(127, 127, 127), 50); // White
  theaterChase(strip.Color(127, 0, 0), 50); // Red
  theaterChase(strip.Color(0, 0, 127), 50); // Blue

  rainbow(20);
  rainbowCycle(20);
  theaterChaseRainbow(50);
  */

  
  if (Serial.available() > 0) {
    incomingStr = Serial.readStringUntil('\n');
  }

 
  if (incomingStr.startsWith("alp://")) {
    if (incomingStr.substring(6,10) == "cust") {
      int cmdSeperator = incomingStr.indexOf('/',11);
      int rgSeperator = incomingStr.indexOf('/', cmdSeperator + 1);
      int gbSeperator = incomingStr.indexOf('/', rgSeperator + 1);
      int delaySeperator = incomingStr.indexOf('/', gbSeperator + 1);
      int cycleSeperator = incomingStr.indexOf('/', delaySeperator+1);
      
      String cmd = incomingStr.substring(11,cmdSeperator);
      int r = (incomingStr.substring(cmdSeperator + 1, rgSeperator)).toInt();
      int g = (incomingStr.substring(rgSeperator + 1, gbSeperator)).toInt();
      int b = (incomingStr.substring(gbSeperator + 1, delaySeperator)).toInt();
      int delayTime = (incomingStr.substring(delaySeperator + 1, cycleSeperator)).toInt();
      int cycleTime = (incomingStr.substring(cycleSeperator+1)).toInt();

//TODO: Make If/Else
      if (cmd.equals("colorOn")) {
        colorOn(strip.Color(r, g, b));
      }

      if (cmd.equals("wipe")) {
        colorWipe(strip.Color(r, g, b), delayTime);
      }

      if (cmd.equals("theaterChase")) {
        theaterChase(strip.Color(r, g, b), delayTime, cycleTime);
      }

      if (cmd.equals("off")) {
        allOff();
      }

      if (cmd.equals("theaterChaseOpp")) {
        theaterChaseOpposite(strip.Color(r, g, b), delayTime, cycleTime);
      }

      if (cmd.equals("combine")) {
        combine(strip.Color(r, g, b), delayTime);
      }

      if (cmd.equals("combineOpp")) {
        combineOpposite(strip.Color(r, g, b), delayTime);
      }

      if (cmd.equals("wipeOpp")) {
        colorWipeOpposite(strip.Color(r, g, b), delayTime);
      } 
    }
  }
}

void combine(uint32_t c, uint8_t wait) {
  for (int i = 0; i < (strip.numPixels() / 2) + 1; i++) {
    strip.setPixelColor(i , c);
    strip.setPixelColor(strip.numPixels() - i, c);
    strip.show();
    delay(wait);
  }
}

void combineOpposite(uint32_t c, uint8_t wait) {
  for (int i = 0; i < strip.numPixels() / 2; i++) {
    strip.setPixelColor((strip.numPixels() / 2) - i, c);
    strip.setPixelColor((strip.numPixels() / 2) + i, c);
    strip.show();
    delay(wait);
  }
}
void colorOn(uint32_t c) {
  for(uint16_t i=0; i<strip.numPixels(); i++) {
    strip.setPixelColor(i, c);
  }
  strip.show();
}

void allOff() {
  for(uint16_t i=0; i<strip.numPixels(); i++) {
    strip.setPixelColor(i, strip.Color(0, 0, 0));
  }
  strip.show();  
}

// Fill the dots one after the other with a color
void colorWipe(uint32_t c, uint8_t wait) {
  for(uint16_t i=0; i<strip.numPixels(); i++) {
    strip.setPixelColor(i, c);
    strip.show();
    delay(wait);
  }
}

void colorWipeOpposite(uint32_t c, uint8_t wait) {
  for(uint16_t i=0; i<strip.numPixels(); i++) {
    strip.setPixelColor(60 - i, c);
    strip.show();
    delay(wait);
  }
}

void colorWipeOppositeOld(uint32_t c, uint8_t wait) {
  for(uint16_t i=0; i<strip.numPixels(); i++) {
    strip.setPixelColor(60 - i, c);
    strip.show();
    delay(wait);
  }
}

void rainbow(uint8_t wait) {
  uint16_t i, j;

  for(j=0; j<256; j++) {
    for(i=0; i<strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel((i+j) & 255));
    }
    strip.show();
    delay(wait);
  }
}

// Slightly different, this makes the rainbow equally distributed throughout
void rainbowCycle(uint8_t wait) {
  uint16_t i, j;

  for(j=0; j<256*5; j++) { // 5 cycles of all colors on wheel
    for(i=0; i< strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel(((i * 256 / strip.numPixels()) + j) & 255));
    }
    strip.show();
    delay(wait);
  }
}

//Theatre-style crawling lights.
void theaterChase(uint32_t c, uint8_t wait, int cycles) {
  for (int j=0; j<cycles; j++) {  //do 10 cycles of chasing
    for (int q=0; q < 3; q++) {
      for (uint16_t i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, c);    //turn every third pixel on
      }
      strip.show();
      
      delay(wait);

      for (uint16_t i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, 0);        //turn every third pixel off
      }
    }
  }
}

void theaterChaseOpposite(uint32_t c, uint8_t wait, int cycles) {
  for (int j=0; j<cycles; j++) {  //do 10 cycles of chasing
    for (int q=3; q > 0; q--) {
      for (uint16_t i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, c);    //turn every third pixel on
      }
      strip.show();
      
      delay(wait);

      for (uint16_t i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, 0);        //turn every third pixel off
      }
    }
  }
}

//Theatre-style crawling lights with rainbow effect
void theaterChaseRainbow(uint8_t wait) {
  for (int j=0; j < 256; j++) {     // cycle all 256 colors in the wheel
    for (int q=0; q < 3; q++) {
      for (uint16_t i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, Wheel( (i+j) % 255));    //turn every third pixel on
      }
      strip.show();

      delay(wait);

      for (uint16_t i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, 0);        //turn every third pixel off
      }
    }
  }
}

// Input a value 0 to 255 to get a color value.
// The colours are a transition r - g - b - back to r.
uint32_t Wheel(byte WheelPos) {
  WheelPos = 255 - WheelPos;
  if(WheelPos < 85) {
    return strip.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  }
  if(WheelPos < 170) {
    WheelPos -= 85;
    return strip.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
  WheelPos -= 170;
  return strip.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
}
