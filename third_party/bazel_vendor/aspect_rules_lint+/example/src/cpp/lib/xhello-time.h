#ifndef LIB_HELLO_TIME2_H_
#define LIB_HELLO_TIME2_H_

#include <stdio.h>

#include <string>

inline void print_localtime3() {
  std::string a = "time";
  for (int i = 0; i < a.size(); ++i) {
    printf("%s", a.c_str()[i]);
  }
}
#endif
