	.data
a:
	10
	.text
main:
	load %x0, $a, %x4			;x4=a
	divi %x4, 2, %x4			;x4=x4/2
	bne %x0, %x31, odd			;if odd remainder!=0 then go to odd branch
	subi %x0, 0, %x10			
	subi %x10, 1, %x10			;x10=-1
	end
odd:
	subi %x0, 0, %x10			
	addi %x0, 1, %x10			;x10=1
	end