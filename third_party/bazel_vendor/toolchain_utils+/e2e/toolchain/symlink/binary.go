package main

import (
	"fmt"
	"os"
	"path"
)

func main() {
	fmt.Fprintf(os.Stdout, "Argument zero: %s\n", path.Base(os.Args[0]))
}
