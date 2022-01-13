package processor.memorysystem;

public class Cache {
    private int blockSize;
    private int cacheSize;
    private int cacheAssociativity = 2;
    private int cacheLineSize = 4;

    private int noOfAccess = 0;
    private int noOfMiss = 0;
    private int noOfHit = 0;

    private int [][] cacheBlocks;
    private int [][] LRU;


    public Cache(int cacheSize ) {
        this.blockSize = cacheSize / cacheAssociativity / cacheLineSize;
        this.cacheSize = cacheSize;
        cacheBlocks = new int[blockSize][cacheAssociativity];

        // initialize cache blocks
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < cacheAssociativity; j++) {
                cacheBlocks[i][j] = 0;
            }
        }

        // initialize LRU
        LRU = new int[blockSize][cacheAssociativity];
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < cacheAssociativity; j++) {
                LRU[i][j] = 0;
            }
        }

    }


    // get the Block index from address
    public int getBlockIndex(int address){
        return (address / cacheLineSize ) % (blockSize);
    }

    // get the tag from address
    public int getTag(int address){
        return (address / cacheLineSize / blockSize);
    }


    // check if the address is in the cache
    public boolean isInCache(int address) {
        noOfAccess ++;
        // get tag from address
        int tag = getTag(address);

        // get block index from address
        int blockIndex = getBlockIndex(address);

        // check if the tag is in the block use for loop
        for (int i = 0; i < cacheAssociativity; i++) {
            if (cacheBlocks[blockIndex][i] == tag) {
                noOfHit ++;
                return true;
            }
        }
        noOfMiss ++;
        return false;
    }

    // replace the LRU block
    public void replaceLRU(int address){
        // get tag from address
        int tag = getTag(address);

        // get block index from address
        int blockIndex = getBlockIndex(address);

        // find the LRU block
        int max = Integer.MIN_VALUE;
        int maxIndex = 0;
        for (int i = 0; i < cacheAssociativity; i++) {
            if (LRU[blockIndex][i] > max) {
                max = LRU[blockIndex][i];
                maxIndex = i;
            }
        }

        // replace the LRU block
        cacheBlocks[blockIndex][maxIndex] = tag;
        LRU[blockIndex][maxIndex] = 0;

        // update LRU
        for (int i = 0; i < cacheAssociativity; i++) {
            LRU[blockIndex][i]++;
        }
    }


    // add address to cache
    public void addToCache(int address) {
        // get tag from address
        int tag = getTag(address);

        // get block index from address
        int blockIndex = getBlockIndex(address);

        // check if the tag is in the block use for loop
        for (int i = 0; i < cacheAssociativity; i++) {
            if (cacheBlocks[blockIndex][i] == tag) {
                return;
            }
        }

        // add tag to the block
        for (int i = 0; i < cacheAssociativity; i++) {
            if (cacheBlocks[blockIndex][i] == 0) {
                cacheBlocks[blockIndex][i] = tag;
                return;
            }
            // if all the blocks are full
            if (i == cacheAssociativity - 1) {
                // replace the block
                replaceLRU(address);
            }

        }

    }

    // get the number of access
    public int getNoOfAccess() {
        return noOfAccess;
    }

    // get the number of miss
    public int getNoOfMiss() {
        return noOfMiss;
    }

    // get the number of hit
    public int getNoOfHit() {
        return noOfHit;
    }


}






