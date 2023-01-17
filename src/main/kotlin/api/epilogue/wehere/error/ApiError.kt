package api.epilogue.wehere.error

class ApiError(val errorCause: ErrorCause, customMessage: String? = null) : Error(customMessage ?: errorCause.message)