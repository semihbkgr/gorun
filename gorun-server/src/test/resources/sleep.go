package main

import "time"

func main() {
	println("Start")
	time.Sleep(500 * time.Millisecond)
	println("...")
	time.Sleep(500 * time.Millisecond)
	print("End")
}
