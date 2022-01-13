	.data
n:
	10
	.text
main:
	load %x0, $n, %x4  			;x4=n
	addi %x0, 65535, %x5 		;x5=2power16-1
	addi %x0, 0, %x6  			;x6=0
	store %x6, 0, %x5			;store x6 at address x5
	addi %x0, 1, %x11 			;for 3 special cases n=1,2,3
	addi %x0, 2, %x12
	addi %x0, 3, %x13
	beq %x4, %x11, xfourisone 
	beq %x4, %x12, xfouristwo
	beq %x4, %x13, xfouristhree
	addi %x0, 1, %x7			;x7=1
	addi %x0, 1, %x8			;x8=1
	addi %x0, 1, %x9			;x9=1
	subi %x5, 1, %x5
	store %x7, 0, %x5
	subi %x5, 1, %x5
	store %x8, 0, %x5
	subi %x4, 2, %x4
recursion:
	beq %x9, %x4, return		;if x6=n return
	add %x7, %x8, %x6			;x6=x7+x8
	add %x0, %x7, %x8			;x8=x7
	add %x0, %x6, %x7			;x7=x6
	subi %x5, 1, %x5			;x5=x5-1 (address)
	store %x6, 0, %x5			;store x6 at address x5
	addi %x9, 1, %x9			;x6=x6+1
	jmp recursion
return:
	end
xfourisone:
	end
xfouristwo:
	subi %x5, 1, %x5
	addi %x0, 1, %x7
	store %x7, 0, %x5
	end
xfouristhree:
	subi %x5, 1, %x5
	addi %x0, 1, %x7
	store %x7, 0, %x5
	subi %x5, 1, %x5
	addi %x0, 1, %x8
	store %x8, 0, %x5
	end