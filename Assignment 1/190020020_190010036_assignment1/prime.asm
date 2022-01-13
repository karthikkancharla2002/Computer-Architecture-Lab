	.data
a:
	10
	.text
main:
	load %x0, $a, %x4;				x4 = a
	blt %x4, 2, notPrime			;			if x4 < 2 then NotPrime
	addi %x0, 2, %x5;				x5 = 2
loop:
	beq %x5, %x4, prime				;			if x5 == x4 then Prime  
	div %x4, %x5, %x6;				x6 = x4/x5  x31 = Remainder
	beq %x31, %x0, notPrime			;			if x31 == 0 then NotPrime
	addi %x5, 1, %x5;				x5+=1
	jmp loop
prime:
	addi %x10, 1, %x10		; x10 = 1
	end
notPrime:
	subi %x10, 1, %x10		; x10 = -1
	end
