package com.jackbradshaw.oksp.application.loaded.apploader

import com.jackbradshaw.oksp.application.Application

interface ApplicationLoader {
  fun load(): Application
}
