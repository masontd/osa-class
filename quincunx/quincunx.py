#!/usr/bin/env python3
#BY MASON DONNOHUE
"""
The *bean machine*, also known as the *Galton Board* or *quincunx*, is a device invented by Sir Francis Galton to demonstrate the central limit theorem, in particular that the normal distribution is approximate to the binomial distribution.
"""

import argparse
import random
import threading


class Board:
    """
    Class Board
    
    Contains multiple bins that collect beans
    Contains multiple levels of pegs
    """

    def __init__(self, bins: int):
        """Make a new board of the specified size"""
        self._bins = [0] * bins
        self._pegs = bins // 2 #number of LEVELS of pegs - 2 sublevels in each level

    def status(self):
        """Print status"""
        sum = 0
        for bin in self._bins:
            sum += bin
            print("|{:^5}".format(bin),end='')
        print("|   " + str(sum))

        #returnstr = ""
        #for i in range(len(self._bins)):
        #    returnstr += "| " + str(self._bins[i]) + " "
        #returnstr += "|" + " " + str(pos)
        #return returnstr

    def __len__(self):
        """Return the board size"""
        return len(self._bins)

    def __getitem__(self, idx: int):
        """Get number of beans in the specified bin"""
        return self._bins[idx]

    def __setitem__(self, idx: int, new_value: int):
        """Set number of beans in the specified bin"""
        self._bins[idx] = new_value

    @property
    def pegs(self):
        """Return number of levels of pegs"""
        return self._pegs


class Bean(threading.Thread):
    """
    Class Bean

    Data members: board, current position, probability, lock
    """

    def __init__(self, board: object, start: int, prob: float, lock: object):
        """Make a new Bean"""
        self.board = board
        self.pos = start
        self.prob = prob #actually compute with random?
        self.lock = lock
        self.board[start] += 1
        threading.Thread.__init__(self, target=self.run)
        #call to super()?

    def move_left(self):
        """Move a bean left"""
        #double check that if you're moving left its not less than 0
        if not((self.pos-1)<0):
            self.board[self.pos] -= 1
            self.pos -= 1
            self.board[self.pos] += 1
        

    def move_right(self):
        """Move a bean right"""
        #double check to make sure the index does not get greater (self.pos) than number of bins
        if not((self.pos+1>len(self.board))):
            self.board[self.pos] -= 1
            self.pos += 1
            self.board[self.pos] += 1

    def run(self):
        """Run a bean through the pegs"""
        #compute chance and whether to move it left or right
        #use lock here
        self.lock.acquire()
        try:
            for i in range(self.board.pegs):
                chance = random.random()
                if chance < self.prob/2:
                    # go left (left-left)
                    self.move_left()
                elif chance < self.prob:
                    #go right (right-right)
                    self.move_right()
                else:
                    #right-left or left-right
                    pass
            
            #acquired lock
            #for loop
            #compute whether to move bean left or right

        finally:
            self.lock.release()


def main():
    """Main function"""
    # Parse command-line arguments
    parser = argparse.ArgumentParser(description="Process the arguments.")
    parser.add_argument('--beans', action="store", dest="beanNum", type=int, default=1000)
    parser.add_argument('--bins', action="store", dest="binNum", type=int, default=11)
    parser.add_argument('--start', action="store", dest="start", type=int, default=5)
    parser.add_argument('--prob', action="store", dest="prob", type=float, default=0.5)
    results = parser.parse_args()
    print("Beans: {}, bins: {}, start: {}, prob: {}".format(results.beanNum, results.binNum, results.start, results.prob))

    print("Start")
    # Create a list of jobs
    jobs = []
    # Create a shared lock
    lock = threading.Lock()
    # Create a board
    board = Board(results.binNum)
    #Create jobs (beans)
    for particle in range(1000):
        #t = threading.Thread(target=Bean, args=(board, results.start, results.prob, lock))
        t = Bean(board, results.start, results.prob, lock)
        jobs.append(t)
        #t.start()
    # Print the board status
    board.status()
    # Start jobs
    for beanidx in range(len(jobs)):
        jobs[beanidx].start()
    # Stop jobs JOIN THEM
    for beanidx in range(len(jobs)):
        jobs[beanidx].join()
    
    #main_thread = threading.main_thread()
    #for t in threading.enumerate():
    #    if t is not main_thread:
    #        t.join()


    # Print the board status
    board.status()
    print("Done")


if __name__ == "__main__":
    main()

