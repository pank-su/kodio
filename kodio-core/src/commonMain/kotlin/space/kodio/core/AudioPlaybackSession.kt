package space.kodio.core

import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration

/**
 * Represents an active playback session.
 */
interface AudioPlaybackSession {

    /** A flow that emits the current state of the playback. */
    val state: StateFlow<State>

    /** A flow that emits the current loaded audio data. */
    val audioFlow: StateFlow<AudioFlow?>

    /** A flow that emits the current playback position. */
    val position: StateFlow<Duration>

    /** A flow that emits the total duration of the loaded audio, if known. */
    val duration: StateFlow<Duration?>

    /**
     * The minimum interval between position updates.
     * Default is usually around 20ms.
     * Set a higher value (e.g. 200.milliseconds) to reduce UI updates.
     * Set a lower value (e.g. 10.milliseconds) for smoother progress bars.
     */
    var positionUpdateInterval: Duration

    /** Loads the given audio data. */
    suspend fun load(audioFlow: AudioFlow, duration: Duration? = null)

    /** Starts playback of the given audio data. */
    suspend fun play()

    /** Pauses the playback. */
    fun pause()
    
    /** Resumes paused playback. */
    fun resume()

    /** Stops the playback entirely. */
    fun stop()

    /**
     * Represents the state of a playback session.
     */
    sealed class State {
        data object Idle : State()
        data object Ready : State()
        data object Playing : State()
        data object Paused : State()
        data object Finished : State()
        data class Error(val error: Throwable) : State()
    }
}