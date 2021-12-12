INSERT INTO snippets(version, display_order, title, brief, explanation, code)
VALUES (1, -1, 'Hello World', 'brief', 'Hello word code snippet', 'package main
import "fmt"
func main() {
	fmt.Println("hello world")
}');
INSERT INTO snippets(version, display_order, title, brief, explanation, code)
VALUES (1, -1, 'Input Test', 'brief', 'Takes your name as input and prints it', 'package main
import "fmt"
func main() {
	fmt.Println("hello world")
	var name string
	fmt.Scanln(&name)
	fmt.Println(name)
}');
INSERT INTO snippets(version, display_order, title, brief, explanation, code)
VALUES (1, -1, 'For Loop', 'brief', 'Simple for loop code snippet', 'package main
import "fmt"
func main() {
	for i := 1;
i<=5;
i++ {
		fmt.Printf("Welcome %d times\n",i)
	}
}');
INSERT INTO snippets(version, display_order, title, brief, explanation, code)
VALUES (1, -1, 'Blocking For Loop', 'brief', 'For loop blocking thread in each step', 'package main
import (
	"fmt"
	"time"
)
func main() {
	for i := 1;
i<=5;
i++ {
		fmt.Printf("Start Unix Time: %v\n", time.Now().Unix())
	time.Sleep(1 * time.Second)	}
}');
