package com.github.lucarosellini.util;

/**
 * This class holds all the constants representing all the properties in the message bundle.
 *
 * The naming convention here is:
 * - properties ending with the suffix '_speech' are strings that will be sent to Alexa TTS engine. These
 *          props might contain SSML tags.
 * - properties NOT ending with '_speech' might hold whathever message the developer might need.
 */
public class Messages {
    public static final String WELCOME_SPEECH = "welcome_speech";
    public static final String HELLOWORLD_SPEECH = "helloworld_speech";
    public static final String GOODBYE_SPEECH = "goodbye_speech";
    public static final String HELP_SPEECH = "help_speech";
    public static final String CANCEL_SPEECH = "cancel_speech";
    public static final String NAVIGATEHOME_SPEECH = "navigatehome_speech";
    public static final String FALLBACK_SPEECH = "fallback_speech";

    /*
     * Add here all the message name properties you might need, remember to add any new property to all the
     * message bundle files with the proper translation.
     */
}
