package main

import "fmt"

func main() {
	fmt.Println("Hello this is code run integration test")
	fmt.Print("Write your name : ")
	var name string
	fmt.Scanln(&name)
	fmt.Printf("Your name %s", name)
}
