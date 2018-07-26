# Stack Calculator

## Preamble 

Implementation of stack calculator via REST API endpoints. Supports addition, substraction, multiplication and division.

## API overview

The API is RESTFUL and generally returns integers with JSON messages for errors. 

You can test the service by forking the repository. After you have all your files local you can start the service using [leiningen](https://leiningen.org), by writing the following commands:

`lein deps
 lein run`

This will start the [jetty](https://www.eclipse.org/jetty/) server on localhost on port 8080. Now you can use the API endpoints on `localhost:8080/calc/...`

## Usage

The stack calculator saves the elements in custom datastructure stack. Currently the application supports up to 3 stacks with relative id's. There is a number of functionalities supported by the service such as peek, push, pop, add, subtract, multiply, and divide. These functionalities are described in this chapter.

### Peek

Using the URL:

`localhost:8080/calc/:id/peek`

Will return the top element of the stack without making any changes to the said stack. If the stack is empty when peek is called the result will be:

    {
      "error": "stack is empty"
    }

### Pop

Using the URL:

`localhost:8080/calc/:id/pop`

Will return and remove the top element of the stack. If the stack is empty when peek is called the result will be:

    {
      "error": "stack is empty"
    }

### Push

Using the URL:

`localhost:8080/calc/:id/push/<n>`

Will push <n> on top of the stack and return this number. If n is not given or isn't an integer the output will be:

    {
      "error": "attr should be a number"
    }

### Add

Using the URL:

`localhost:8080/calc/:id/add`

Will add two top element of the stack, remove them and put their sum in their place. Returns the new top element. If there are not enough elements in the stack for addition, the output will be:

    {
      "error": "Stack doesn't have two top elements"
    }

### Subtract

Using the URL:

`localhost:8080/calc/:id/subtract`

Will subtract two top element of the stack, remove them and put their difference in their place. Returns the new top element. If there are not enough elements in the stack for subtraction, the output will be:

    {
      "error": "Stack doesn't have two top elements"
    }

### Multiply

Using the URL:

`localhost:8080/calc/:id/multiply`

Will multiply two top element of the stack, remove them and put their product in their place. Returns the new top element. If there are not enough elements in the stack for multiplication, the output will be:

    {
      "error": "Stack doesn't have two top elements"
    }

### Divide

Using the URL:

`localhost:8080/calc/:id/multiply`

Will divide two top element of the stack, remove them and put the result of division in their place. Returns the new top element. If there are not enough elements in the stack for division, the output will be:

    {
      "error": "Stack doesn't have two top elements"
    }

## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
