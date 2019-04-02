import threading
import random

board = [[None]]*11


def worker():
    """thread worker function"""
    print('Worker')
    #compute random - left or right
    return random.randint(0,1)


threads = []
for particle in range(1000):
    t = threading.Thread(target=worker)
    board[len(board)//2].append(t)
    t.start()


for step in range(100):
