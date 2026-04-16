package com.jackbradshaw.oksp.application.loader

import com.jackbradshaw.oksp.application.Application

interface ApplicationLoader {
  fun load(): Application
}
