package main

import (
	"fmt"
	"time"
)

const (
	START = "start"
	END   = "end"
)

func main() {
	var command string
	var startTime int
	var endTime int
	for {
		fmt.Printf("Write '%s' to start timer\n", START)
		fmt.Scanln(&command)
		if command == START {
			startTime = time.Now().Minute()
			break
		}
	}
	for {
		fmt.Printf("Write '%s' to end timer\n", END)
		fmt.Scanln(&command)
		if command == END {
			endTime = time.Now().Minute()
			break
		}
	}

	fmt.Printf("Timer, minute: %d", endTime-startTime)

}
