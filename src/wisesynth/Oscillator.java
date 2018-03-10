package wisesynth;

//Author: Ryan Chesnut 
//KSU Senior Project

/*
//C0		16.35 	
//C#0/Db0  	17.32 	
//D0		18.35 	
//D#0/Eb0  	19.45 	
//E0		20.60 
//F0		21.83 	
//F#0/Gb0  	23.12 	
//G0		24.50 	
//G#0/Ab0  	25.96 	
//A0		27.50 
//A#0/Bb0  	29.14 
//B0		30.87 
//C1		32.70 
//C#1/Db1  	34.65 	
//D1		36.71 	
//D#1/Eb1  	38.89 	
//E1		41.20 	
//F1		43.65 	
//F#1/Gb1  	46.25 	
//G1		49.00 	
//G#1/Ab1  	51.91 	
//A1		55.00 	
//A#1/Bb1  	58.27 	
//B1		61.74 	
//C2		65.41 		
//C#2/Db2  	69.30 	
//D2		73.42 	
//D#2/Eb2  	77.78 	
//E2		82.41 	
//F2		87.31 	
//F#2/Gb2  	92.50 	
//G2		98.00 	
//G#2/Ab2  	103.83 	
//A2		110.00 	
//A#2/Bb2  	116.54 	
//B2		123.47 	
//C3		130.81 	
//C#3/Db3  	138.59 	
//D3		146.83 	
//D#3/Eb3  	155.56 	
//E3		164.81 	
//F3		174.61 	
//F#3/Gb3  	185.00 	
//G3		196.00 	
//G#3/Ab3  	207.65 	
//A3		220.00 	
//A#3/Bb3  	233.08 	
//B3		246.94 	
//C4		261.63 	
//C#4/Db4  	277.18 	
//D4		293.66 	
//D#4/Eb4  	311.13 	
//E4		329.63 	
//F4		349.23 	
//F#4/Gb4  	369.99 	
//G4		392.00 	
//G#4/Ab4  	415.30 	
//A4		440.00 	
//A#4/Bb4  	466.16 	
//B4		493.88 	
//C5		523.25 	
//C#5/Db5  	554.37 	
//D5		587.33 	
//D#5/Eb5  	622.25 	
//E5		659.25 	
//F5		698.46 	
//F#5/Gb5  	739.99 	
//G5		783.99 	
//G#5/Ab5  	830.61 	
//A5		880.00 	
//A#5/Bb5  	932.33 	
//B5		987.77 	
//C6		1046.50 
//C#6/Db6  	1108.73 
//D6		1174.66 
//D#6/Eb6  	1244.51 
//E6		1318.51 
//F6		1396.91 
//F#6/Gb6  	1479.98 
//G6		1567.98 
//G#6/Ab6  	1661.22 
//A6		1760.00 
//A#6/Bb6  	1864.66 
//B6		1975.53 
//C7		2093.00 
//C#7/Db7  	2217.46 
//D7		2349.32 
//D#7/Eb7  	2489.02 
//E7		2637.02 
//F7		2793.83 
//F#7/Gb7  	2959.96 
//G7		3135.96 
//G#7/Ab7  	3322.44 
//A7		3520.00 
//A#7/Bb7  	3729.31 
//B7		3951.07 
//C8		4186.01 
//C#8/Db8  	4434.92 
//D8		4698.63 
//D#8/Eb8  	4978.03 
//E8		5274.04 
//F8		5587.65 
//F#8/Gb8  	5919.91 
//G8		6271.93 
//G#8/Ab8  	6644.88 
//A8		7040.00 
//A#8/Bb8  	7458.62 
//B8		7902.13 
 */


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Oscillator{

	static Oscillator os = new Oscillator(); 
	public enum WAVE {SIN, SAW, TRI, SQU, REVSAW};
	private double frequency = 0; 
	public double amplitude = 35; 
	private static final double PI = Math.PI; 
	private static final int SAMPLE_RATE = 44100;
	public double sampleInterval = 0.0;
	boolean flag; 
	public AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, 8, 2, true, false);  
	public byte [] audioBuffer = new byte[50000]; 
	private double [] noteArray = {65.41, 73.42 ,82.41, 87.31, 98.00, 110.00, 123.47, 130.81,  /* Plays C Major scale with arppegiation */
			130.81, 123.47, 110.00, 98.00, 87.31, 82.41, 73.42, 65.41, 
			65.41, 82.41, 98.00, 130.81, 98.00, 82.41, 65.41};   

	public Oscillator()
	{
	}

	public synchronized void play (byte [] ab) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		
		InputStream stream = new ByteArrayInputStream(ab);
		SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat);
			
			line.open(audioFormat, 44000);
			line.start();
			for(;;){	
							
				if(!flag){
					line.stop();
					line.drain();
					line.close();
					break;
				}
						              
                line.write(ab, 0, ab.length -2);

			}
	}

	// Generates a sine wave //
	public byte[] generateSineWave(byte[] audioBuffer, double sampleInterval, double amplitude){
		for(int i=0; i < audioBuffer.length; i++){
			double angle = (( 1 * 2 * PI * i)/sampleInterval);
			audioBuffer[i] = (byte)(amplitude * Math.sin(angle));
		}
		return audioBuffer; 
	}

	// Generates a square wave //
	public byte[] generateSquareWave(byte[] audioBuffer, double sampleInterval, double amplitude){

		for(int i=0; i < audioBuffer.length; i++){
			double angle = (( 1 * 2 * PI * i)/sampleInterval);
			double angle2 = (( 3 * 2 * PI * i)/sampleInterval);
			double angle3 = (( 5 * 2 * PI * i)/sampleInterval);
			double angle4 = (( 7 * 2 * PI * i)/sampleInterval);
			double angle5 = (( 9 * 2 * PI * i)/sampleInterval);

			audioBuffer[i]=(byte)(amplitude * Math.sin(angle) 
					+ amplitude * Math.sin(angle2)
					+ amplitude * Math.sin(angle3)
					+ amplitude * Math.sin(angle4)
					+ amplitude * Math.sin(angle5));
		}

		return audioBuffer; 
	}

	// Generates a triangle wave //
	public byte[] generateTriangleWave(byte[] audioBuffer, double sampleInterval, double amplitude){

		for(int i=0; i < audioBuffer.length; i++){
			double angle = (( 1 * 2 * PI * i)/sampleInterval);
			double angle2 = (( 3 * 2 * PI * i)/sampleInterval);
			double angle3 = (( 5 * 2 * PI * i)/sampleInterval);
			double angle4 = (( 7 * 2 * PI * i)/sampleInterval);
			double angle5 = (( 9 * 2 * PI * i)/sampleInterval);
			double angle6 = (( 11 * 2 * PI * i)/sampleInterval);
			double angle7 = (( 13 * 2 * PI * i)/sampleInterval);
			double angle8 = (( 15 * 2 * PI * i)/sampleInterval);
			double angle9 = (( 17 * 2 * PI * i)/sampleInterval);
			double angle10 = (( 19 * 2 * PI * i)/sampleInterval);
			double angle11 = (( 21 * 2 * PI * i)/sampleInterval);



			double factor = ((8/PI*PI)); 

			audioBuffer[i]=(byte) ( factor * amplitude * (- Math.sin(angle) 
					+ (Math.sin(angle2)/(3*3)) 
					- (Math.sin(angle3)/(5*5)) 
					+ (Math.sin(angle4)/(7*7))
					- (Math.sin(angle5)/(9*9))
					+ (Math.sin(angle6)/(11*11))
					- (Math.sin(angle7)/(13*13))
					+ (Math.sin(angle8)/(15*15))
					- (Math.sin(angle9)/(17*17))
					+ (Math.sin(angle10)/(19*19))
					- (Math.sin(angle11)/(21*21))

					));
		}

		return audioBuffer;
	}


	public byte[] generateSawtoothWave(byte[] audioBuffer, double sampleInterval, double amplitude){

		for(int i=0; i < audioBuffer.length; i++){
			double angle1 = (( 1 * 2 * PI * i)/sampleInterval);
			double angle2 = (( 2 * 2 * PI * i)/sampleInterval);
			double angle3 = (( 3 * 2 * PI * i)/sampleInterval);
			double angle4 = (( 4 * 2 * PI * i)/sampleInterval);
			double angle5 = (( 5 * 2 * PI * i)/sampleInterval);
			double angle6 = (( 6 * 2 * PI * i)/sampleInterval);
			double angle7 = (( 7 * 2 * PI * i)/sampleInterval);
			double angle8 = (( 8 * 2 * PI * i)/sampleInterval);
			double angle9 = (( 9 * 2 * PI * i)/sampleInterval);
			double angle10 = (( 10 * 2 * PI * i)/sampleInterval);


			double factor = (amplitude/(2)-(amplitude/PI)); 

			audioBuffer[i]=(byte)
					(factor * (amplitude * Math.sin(angle1) 
							+ amplitude * Math.sin(angle2)
							+ amplitude * Math.sin(angle3)
							+ amplitude * Math.sin(angle4)
							+ amplitude * Math.sin(angle5)
							+ amplitude * Math.sin(angle6)
							+ amplitude * Math.sin(angle7)
							+ amplitude * Math.sin(angle8)
							+ amplitude * Math.sin(angle9)
							+ amplitude * Math.sin(angle10)

							));
		}

		return audioBuffer;
	}

	// Generates a triangle wave //
	public byte[] generateRevSawtoothWave(byte[] audioBuffer, double sampleInterval, double amplitude){

		for(int i=0; i < audioBuffer.length; i++){
			double angle = (( 1 * 2 * PI * i)/sampleInterval);
			double angle2 = (( 2 * 2 * PI * i)/sampleInterval);
			double angle3 = (( 3 * 2 * PI * i)/sampleInterval);
			double angle4 = (( 4 * 2 * PI * i)/sampleInterval);
			double angle5 = (( 5 * 2 * PI * i)/sampleInterval);
			double angle6 = (( 6 * 2 * PI * i)/sampleInterval);
			double angle7 = (( 7 * 2 * PI * i)/sampleInterval);
			double angle8 = (( 8 * 2 * PI * i)/sampleInterval);
			double angle9 = (( 9 * 2 * PI * i)/sampleInterval);
			double angle10 = (( 10 * 2 * PI * i)/sampleInterval);

			double factor = ((2/PI)); 

			audioBuffer[i]=(byte) ( factor * amplitude* (-Math.sin(angle) 
					+ (Math.sin(angle2)/2)
					- (Math.sin(angle3)/3)
					+ (Math.sin(angle4)/4)
					- (Math.sin(angle5)/5)
					+ (Math.sin(angle6)/6)
					- (Math.sin(angle7)/7)
					+ (Math.sin(angle8)/8)
					- (Math.sin(angle9)/9)
					+ (Math.sin(angle10)/10)

					));
		}

		return audioBuffer;
	}


	public double getSampleInterval(double frequency) {
		sampleInterval = (double)(SAMPLE_RATE / frequency);
		return sampleInterval;
	}
	public void setSampleInterval(double sampleInterval) {
		this.sampleInterval = sampleInterval;
	}
	public double getFrequency() {
		return frequency;
	}
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	public void setAmplitube(int volume) {
		this.amplitude = volume; 
	}
	public static void main(String args[]) throws LineUnavailableException, IOException, UnsupportedAudioFileException{

		for(int i=0; i<os.noteArray.length; i++){
		os.setFrequency(os.noteArray[i]);
		os.getSampleInterval(os.noteArray[i]);
		os.generateSineWave(os.audioBuffer, os.sampleInterval, os.amplitude);
		os.play(os.audioBuffer);
		}
	}

//===========================For Maz (Delay implemetation)=========================================//	
	
/*	for(int j=0; j<os.audioBuffer.length; j++)
		os.audioBuffer[j] = ((byte) ((os.audioBuffer[j] + audioBuffer2[j]) + audioBuffer3[j]/(3))); 

	InputStream is = new ByteArrayInputStream(os.audioBuffer);
	Delay delay = new Delay(50000, .5f);
	 byte[] targetArray = IOUtils.toByteArray(new FilteredSoundStream(is, delay));
	 os.play(os.audioFormat, targetArray);
	 System.exit(0);*/

	 
//====================================================================================================//

	// Uncomment to hear sine wave //

	/*		os.generateSineWave(os.audioBuffer, os.sampleInterval, os.amplitude);
		os.setFrequency(32.70);
		os.setSampleInterval(32.70);
		System.out.println("playing sine wave at "+ os.frequency + " hertz"); 
	 	os.play(os.audioFormat, os.audioBuffer);
	 */				 

	// Uncomment to hear square wave //
	/*		 os.generateSquareWave(os.audioBuffer, os.sampleInterval, os.amplitude);
		 os.setFrequency(32.70);
		 os.setSampleInterval(32.70);
		 System.out.println("playing square wave at "+ os.frequency + " hertz"); 
		 os.play(os.audioFormat, os.audioBuffer);
	 */	

}


