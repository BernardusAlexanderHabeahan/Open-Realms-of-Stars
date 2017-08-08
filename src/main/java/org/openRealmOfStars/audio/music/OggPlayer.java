package org.openRealmOfStars.audio.music;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.openRealmOfStars.utilities.ErrorLogger;

import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, see http://www.gnu.org/licenses/
*
*
* Ogg Player using JOrbis library
*
*/
public class OggPlayer {

  /**
   * Ogg Page containing Ogg packets and header
   */
  private Page joggPage = new Page();
  /**
   * Ogg packet packed raw audio data
   */
  private Packet joggPacket = new Packet();
  /**
   * Ogg Stream State
   */
  private StreamState joggStreamState = new StreamState();
  /**
   * Ogg Sync State
   */
  private SyncState joggSyncState = new SyncState();

  /**
   * Orbis technical information like bit rate and number of channels.
   */
  private Info jorbisInfo = new Info();
  /**
   * Orbis Comment is for Orbis textual infomartion
   */
  private Comment jorbisComment = new Comment();
  /**
   * Orbis DSP State aka Digital Signal Processor
   */
  private DspState jorbisDspState = new DspState();
  /**
   * Orbis Block containing ready audio
   */
  private Block jorbisBlock = new Block(jorbisDspState);

  /**
   * Buffered Input Stream for reading the Ogg Stream
   */
  private BufferedInputStream oggStream;

  /**
   * Buffer size in bytes
   */
  private static final int BUFFER_SIZE = 8192;

  /**
   * Read byte buffer
   */
  private byte[] buffer = null;

  /**
   * Conversion buffer size, double the read byte buffer
   */
  private int conversionSize = BUFFER_SIZE * 2;

  /**
   * Conversion byte buffer.
   */
  private byte[] conversionBuffer;

  /**
   * Flag to signal when playing should be stopped
   */
  private boolean stopPlay = false;

  /**
   * Ogg volume, default is 50%
   */
  private static int oggVolume = 50;

  /**
   * Initializes new OggPlayer from Inputstream
   * @param is InputStream containg Ogg Vorbis data
   * @throws IOException if InputStream is null
   */
  public OggPlayer(final InputStream is) throws IOException {
    if (is == null) {
      throw new IOException("No OGG stream available!");
    }
    oggStream = new BufferedInputStream(is);
    oggStream.mark(Integer.MAX_VALUE);
    conversionBuffer = new byte[conversionSize];
  }

  /**
   * Get Ogg PLayer volume
   * @return OGG player volume. This value is between 0 to 100.
   */
  public static int getOggVolume() {
    return oggVolume;
  }

  /**
   * Set Ogg volume
   * @param volume Value between 0 to 100
   */
  public static void setOggVolume(final int volume) {
    if (volume >= 0 && volume <= 100) {
      oggVolume = volume;
    }
  }

  /**
   * Initializes OGG player
   */
  private void initializeJOrbis() {
    joggPacket = new Packet();
    joggPage = new Page();
    joggStreamState = new StreamState();
    joggSyncState = new SyncState();
    jorbisDspState = new DspState();
    jorbisBlock = new Block(jorbisDspState);
    jorbisComment = new Comment();
    jorbisInfo = new Info();
    joggSyncState.init();
  }

  /**
   * Open Audio Output line with number of channels and sampling rate
   * @param channels Number of channels in audio line
   * @param rate SampleRate for audio line
   * @return SourceDataLine If audio line is available, otherwise null
   */
  private SourceDataLine getOutputLine(final int channels, final int rate) {
    AudioFormat audioFormat = new AudioFormat((float) rate, 16, channels,
        true, false);
    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat,
        AudioSystem.NOT_SPECIFIED);
    if (!AudioSystem.isLineSupported(info)) {
      return null;
    }
    SourceDataLine outputLine;
    try {
      outputLine = (SourceDataLine) AudioSystem.getLine(info);
      outputLine.open(audioFormat);
    } catch (LineUnavailableException | IllegalArgumentException ex) {
      return null;
    }
    outputLine.start();
    return outputLine;
  }

  /**
   * Is OGG playing stopped or not
   * @return True if OGG is not playing anymore
   */
  public boolean isStopped() {
    if (oggStream == null) {
      return true;
    }
    return false;
  }

  /**
   * Make Ogg player to stop playing.
   */
  public void stop() {
    stopPlay = true;
  }

  /**
   * Play the OGG from opened InputStream
   * @throws IOException if something fails unexpectedly while playing.
   */
  public void play() throws IOException {
    oggStream.reset();
    initializeJOrbis();
    stopPlay = false;
    boolean chained = false;
    int bytes;
    while (true) {
      boolean endOfStream = false;
      int index = joggSyncState.buffer(BUFFER_SIZE);
      buffer = joggSyncState.data;
      try {
        bytes = oggStream.read(buffer, index, BUFFER_SIZE);
      } catch (IOException e) {
        endOfStream = true;
        break;
      }
      joggSyncState.wrote(bytes);
      if (chained) {
        chained = false;
      } else {
        if (joggSyncState.pageout(joggPage) != 1) {
          if (bytes < BUFFER_SIZE) {
            break;
          } else {
            throw new IOException("Not and OGG stream!");
          }
        }
      }
      joggStreamState.init(joggPage.serialno());
      joggStreamState.reset();
      jorbisInfo.init();
      jorbisComment.init();
      if (joggStreamState.pagein(joggPage) < 0) {
        throw new IOException("Error while reading the first page of"
            + " OGG stream!");
      }
      if (joggStreamState.packetout(joggPacket) != 1) {
        throw new IOException("Error while reading the first OGG header"
            + " packet!");
      }

      if (jorbisInfo.synthesis_headerin(jorbisComment, joggPacket) < 0) {
        throw new IOException("Error while reading the first OGG stream"
            + " doest not contain audio data!");
      }
      int headerCount = 0;
      while (headerCount < 2) {
        while (headerCount < 2) {
          int result = joggSyncState.pageout(joggPage);
          if (result == 0) {
            break; // Need more data
          }
          if (result == 1) {
            joggStreamState.pagein(joggPage);
            while (headerCount < 2) {
              result = joggStreamState.packetout(joggPacket);
              if (result == 0) {
                break;
              }
              if (result == -1) {
                throw new IOException("Secondary header is corrupted!");
              }
              jorbisInfo.synthesis_headerin(jorbisComment, joggPacket);
              headerCount++;
            }
          }
        }
        index = joggSyncState.buffer(BUFFER_SIZE);
        buffer = joggSyncState.data;
        bytes = oggStream.read(buffer, index, BUFFER_SIZE);
        if (bytes == 0 && headerCount < 2) {
          throw new IOException("Ogg file ended before all Vorbis headers!");
        }
        joggSyncState.wrote(bytes);
      }
      conversionSize = BUFFER_SIZE / jorbisInfo.channels;
      jorbisDspState.synthesis_init(jorbisInfo);
      jorbisBlock.init(jorbisDspState);
      // PCM float data, [0][Channels][Samples]
      float[][][] pcmFullData = new float[1][][];
      int[] channelIndexes = new int[jorbisInfo.channels];
      SourceDataLine outputLine = getOutputLine(jorbisInfo.channels,
                                                jorbisInfo.rate);
      FloatControl gainControl = null;
      if (outputLine != null) {
        // Volume set for OGG
        try {
          gainControl = (FloatControl) outputLine.getControl(
              FloatControl.Type.VOLUME);
          gainControl.setValue(gainControl.getMaximum() * getOggVolume() / 100);
        } catch (IllegalArgumentException e) {
          // Sound system does not support volume, let's try master gain then
          try {
            gainControl = (FloatControl) outputLine.getControl(
                FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(
                gainControl.getMinimum() * ((100 - getOggVolume()) / 100));
          } catch (IllegalArgumentException e2) {
            ErrorLogger.log("Your audio system does not support VOLUME or"
                + " MASTER_GAIN control...Playing default volume");
          }
        }
      }
      while (!endOfStream) {
        while (!endOfStream) {
          if (stopPlay) {
            endOfStream = true;
            break;
          }
          int result = joggSyncState.pageout(joggPage);
          if (result == 0) {
            break; // need more data
          }
          if (result != -1) {
            joggStreamState.pagein(joggPage);
            if (joggPage.granulepos() == 0) {
              chained = true;
              endOfStream = true;
              break;
            }
            while (true) {
              result = joggStreamState.packetout(joggPacket);
              if (result == 0) {
                break; // need more data
              }
              if (result != -1) {
                // we have a packet.  Decode it
                int samples;
                if (jorbisBlock.synthesis(joggPacket) == 0) {
                  jorbisDspState.synthesis_blockin(jorbisBlock);
                }
                while ((samples = jorbisDspState.synthesis_pcmout(pcmFullData,
                    channelIndexes)) > 0) {
                  float[][] pcmData = pcmFullData[0];
                  int limit = samples;
                  if (samples > conversionSize) {
                    limit = conversionSize;
                  }
                  // convert doubles to 16 bit signed ints (host order) and
                  // interleave
                  for (int i = 0; i < jorbisInfo.channels; i++) {
                    int ptr = i * 2;
                    int mono = channelIndexes[i];
                    for (int j = 0; j < limit; j++) {
                      int sampleValue = (int) (pcmData[i][mono + j] * 32767.);
                      // First limiting sample value to be between -+32767
                      if (sampleValue > 32767) {
                        sampleValue = 32767;
                      }
                      if (sampleValue < -32768) {
                        sampleValue = -32768;
                      }
                      if (sampleValue < 0) {
                        // Making sample value to signed 16 bit
                        sampleValue = sampleValue | 0x8000;
                      }
                      conversionBuffer[ptr] = (byte) (sampleValue);
                      conversionBuffer[ptr + 1] = (byte) (sampleValue >>> 8);
                      ptr += 2 * (jorbisInfo.channels);
                    }
                  }
                  if (gainControl != null) {
                    gainControl.setValue(
                        gainControl.getMaximum() * getOggVolume() / 100);
                  }
                  if (outputLine != null) {
                    outputLine.write(
                        conversionBuffer, 0, 2 * jorbisInfo.channels * limit);
                  }
                  jorbisDspState.synthesis_read(limit);
                }
              }
            }
            if (joggPage.eos() != 0) {
              endOfStream = true;
            }
          }
        }
        if (!endOfStream) {
          index = joggSyncState.buffer(BUFFER_SIZE);
          buffer = joggSyncState.data;
          try {
            bytes = oggStream.read(buffer, index, BUFFER_SIZE);
          } catch (IOException e) {
            endOfStream = true;
            break;
          }
          if (bytes == -1) {
            break;
          }
          joggSyncState.wrote(bytes);
          if (bytes == 0) {
            endOfStream = true;
          }
        }
      }
      if (outputLine != null) {
        outputLine.close();
      }
      joggStreamState.clear();
      jorbisBlock.clear();
      jorbisDspState.clear();
      jorbisInfo.clear();
      if (stopPlay) {
        break;
      } else {
        oggStream.reset();
      }
    }
    joggSyncState.clear();
    try {
      if (oggStream != null) {
        oggStream.close();
        oggStream = null;
      }
    } catch (Exception e) {
      oggStream = null;
    }
  }

}