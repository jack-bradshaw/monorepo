package main

import (
	"github.com/bazelbuild/rules_go/go/runfiles"
	"io"
	"log"
	"os"
)

func main() {
	if len(os.Args) > 1 {
		log.Fatal("no arguments, please")
	}

	path, err := runfiles.Rlocation("_main/toolchain/runfiles/fixture.txt")
	if err != nil {
		log.Fatalf("no runfile found: %s", err)
	}

	fd, err := os.Open(path)
	if err != nil {
		log.Fatalf("failed to open runfile: %s", err)
	}

	if _, err := io.Copy(os.Stdout, fd); err != nil {
		log.Fatalf("failed to copy to stdout: %s", err)
	}
}
