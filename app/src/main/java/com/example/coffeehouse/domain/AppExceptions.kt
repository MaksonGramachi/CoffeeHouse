package com.example.coffeehouse.domain

sealed class AppException(message: String): Exception(message)

class UserAlreadyExistsException(message: String): AppException(message)
class UserNotExistsException(message: String): AppException(message)
class NullBodyException(message: String): AppException(message)
class UserTokenIsNotAvailableException(message: String): AppException(message)
class UserAuthInformationIsEmpty(message: String): AppException(message)
class UserPasswordsIsNotEqualException(message: String) : AppException(message)

