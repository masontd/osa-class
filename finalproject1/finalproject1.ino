
#include <Wire.h>
#include <Adafruit_SSD1306.h>
#include <Adafruit_GFX.h>

// OLED display TWI address
#define OLED_ADDR   0x3C

Adafruit_SSD1306 display(-1);

#if (SSD1306_LCDHEIGHT != 64)
#error("Height incorrect, please fix Adafruit_SSD1306.h!");
#endif


#define NOTE_E7  2637
#define NOTE_C7  2093
#define NOTE_G7  3136
#define NOTE_G6  1568

 
int outPin = 1; 
int buzzer = 2;
int inPin = 13;         

int mpin1 = 3;
int mpin2 = 4;
int mpin3 = 5;
int mpin4 = 6;
int mpin5 = 7;
int mpin6 = 8;
int mpin7 = 9;
int mpin8 = 10;

int melody[] = {
  NOTE_E7, NOTE_E7, 0, NOTE_E7,
  0, NOTE_C7, NOTE_E7, 0,
  NOTE_G7, 0, 0,  0,
  NOTE_G6, 0, 0, 0,
};
int musicButton = 12;

int musicCount = 16;


byte leds = 0;
int place = 0;


int state = HIGH;      
int reading;           
int previous = LOW;    


long time = 0;         
long debounce = 200;   

void setup()
{
  Serial.begin(9600);
  pinMode(inPin, INPUT);

  //pinMode(mpin1, INPUT_PULLUP);
  pinMode(mpin1, OUTPUT);
  pinMode(mpin2, INPUT_PULLUP);
  pinMode(mpin3, INPUT_PULLUP);
  pinMode(mpin4, INPUT_PULLUP);
  pinMode(mpin5, INPUT_PULLUP);
  pinMode(mpin6, INPUT_PULLUP);
  pinMode(mpin7, INPUT_PULLUP);
  pinMode(mpin8, INPUT_PULLUP);
  pinMode(musicButton, INPUT_PULLUP);
  
  pinMode(buzzer, OUTPUT);
  pinMode(outPin, OUTPUT);
  //Serial.begin(9600);

  display.begin(SSD1306_SWITCHCAPVCC, OLED_ADDR);
  display.clearDisplay();
  display.display();
  display.setTextSize(1);
  display.setTextColor(WHITE);
  display.setCursor(6,22);
  display.println("Arduino Piezo Piano!");
  display.println("     created by;");
  display.println("  Mitchell and Mason");

  
  display.display();
}

void loop()
{
  
  reading = digitalRead(inPin);

  
  if (reading == HIGH && previous == LOW && millis() - time > debounce) {
    if (state == HIGH)
      state = LOW;
    else
      state = HIGH;

    time = millis();    
  }

  //digitalWrite(outPin, state);
  digitalWrite(mpin1, state);
  if (state == HIGH) {
    
    
    //if (digitalRead(mpin1) == LOW){
    //  tone(buzzer, 400, 100);  
      //Serial.println("yee");   
    //}
    if (digitalRead(mpin2) == LOW) {
      Serial.println("note 1 is being pressed");
      tone(buzzer, 600, 100);
    }
    if (digitalRead(mpin3) == LOW) {
      Serial.println("note 2 is being pressed");
      tone(buzzer, 800, 100);
    }
    if (digitalRead(mpin4) == LOW) {
      Serial.println("note 3 is being pressed");
      tone(buzzer, 1000, 100);
    }
    if (digitalRead(mpin5) == LOW) {
      Serial.println("note 4 is being pressed");
      tone(buzzer, 1200, 100);
    }
    if (digitalRead(mpin6) == LOW) {
      Serial.println("note 5 is being pressed");
      tone(buzzer, 1400, 100);
    }
    if (digitalRead(mpin7) == LOW) {
      Serial.println("note 6 is being pressed");
      tone(buzzer, 1600, 100);
    }
    if (digitalRead(mpin8) == LOW) {
      Serial.println("note 7 is being pressed");
      tone(buzzer, 1800, 100);
    }
    if (digitalRead(musicButton) == LOW) {
      //tone(buzzer, 2000, 100);
      for (int thisPin = 0; thisPin < musicCount-1; thisPin++) {
      tone(buzzer, melody[thisPin], 500);
    
      delay(150);
      }
      Serial.println("It's Me Mario! AWOHOOO!");
    }
  }

  previous = reading;
}
