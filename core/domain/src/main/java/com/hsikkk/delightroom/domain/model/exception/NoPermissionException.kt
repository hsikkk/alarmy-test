package com.hsikkk.delightroom.domain.model.exception

class NoPermissionException(
    override val message: String = "permission is not granted"
): Exception()
