package com.example.convert.contract

enum class Direction {
    N {
        override fun move(rover: Rover): Rover {
            rover.y = rover.y + 1
            return rover
        }
    },
    W {
        override fun move(rover: Rover): Rover {
            if (rover.x >= 1) {
                rover.x = rover.x - 1
            }
            return rover
        }
    },
    S {
        override fun move(rover: Rover): Rover {
            if (rover.y >= 1) {
                rover.y = rover.y - 1
            }
            return rover
        }
    },
    E {
        override fun move(rover: Rover): Rover {
            rover.x = rover.x + 1
            return rover
        }
    };

    abstract fun move(rover: Rover): Rover
}