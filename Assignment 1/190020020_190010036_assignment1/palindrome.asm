	.data
a:
	101
	.text
main:
	load %x0, $a, %x4				; x4 = a
	add %x0, %x4, %x5				; x5 = x4
	addi %x0, 0, %x6				; x6 = 0
loop:
	beq %x5, 0, check				; if x5 == 0 then check
	divi %x5, 10, %x5				; x5 = x5/10, x31 = x5%10
	muli %x6, 10, %x6				; x6 = x6*10
	add %x6, %x31, %x6				; x6 = x6 + x31
	jmp loop
check:
	beq %x4, %x6, palindrome		; if x4 == x6 then palindrome
	subi %x10, 1, %x10				; x10 = -1
	end
palindrome:
	addi %x10, 1, %x10				; x10 = 1
	end