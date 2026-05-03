package com.jackbradshaw.obelisk.oksp

import com.jackbradshaw.obelisk.oksp.service.Service

interface ObeliskOkspComponent {
  fun serviceFactory(): Service.Factory
}
