package com.semihbkgr.gorun.message;

public enum Action {

    /*
     * Command Actions
     */

    // [RUN:<code>]
    // Starting code execution
    RUN,

    // [INPUT:<input>]
    // Input to ongoing code execution
    INPUT,

    //[INTERRUPT]
    // Interrupting ongoing code execution
    INTERRUPT,

    /*
     * Acknowledgement Actions
     */

    // [RUN_ACK:<start_time_in_ms>]
    // Acknowledgement of run action
    RUN_ACK,

    // [INPUT_ACK:<input>]
    // Acknowledgement of input action
    INPUT_ACK,

    // [OUTPUT:<output>]
    // Output from ongoing code execution
    OUTPUT,

    // [INTERRUPTED:<execution_time>]
    // Acknowledgement of interrupted code execution
    INTERRUPTED,

    // [COMPLETED:<execution_time>]
    // Acknowledgement of completed code execution
    COMPLETED,

    // [TIMED_OUT:<execution_time>]
    // Acknowledgement of timed out code execution
    TIMED_OUT,

    // [ILLEGAL_ACTION:<reason_text>]
    // Acknowledgement of unexpected operations
    ILLEGAL_ACTION,

    // [SYSTEM_ERROR:<reason_text>]
    // Acknowledgement of system based errors
    SYSTEM_ERROR;

}
