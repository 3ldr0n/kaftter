package kaftter.api.exception

/**
 * Exception used when the user was not found on the summarized tweet table.
 */
class UserNotFoundException(userId: Long) : Exception("User not found. userId=$userId")