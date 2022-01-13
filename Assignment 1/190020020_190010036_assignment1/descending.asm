	.data
a:
	70
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main:
	addi %x0, 0, %x4					; x4 = 0
	load %x0, $n, %x9
loop1:
	beq %x4, %x9, endProgram				; if x4 == n then endProgram
	add %x0, %x4, %x5					; x5 = x4
loop2:
	beq %x5, %x9, endLoop1				; if x5 == n then endLoop1
	load %x4, $a, %x6					; x6 = a[x4]
	load %x5, $a, %x7					; x7 = a[x5]
	blt %x7, %x6, skipSwap				; if x6 < x7 then skipSwap
	add %x0, %x6, %x15					; x15 = x6
	add %x7, %x0, %x6					; x6 = x7
	add %x15, %x0, %x7					; x7 = x15
	store %x6, $a, %x4					; store a + x4 = x6
	store %x7, $a, %x5					; store a + x5 = x7
skipSwap:
	addi %x5, 1, %x5					; x5 +=1
	jmp loop2
endLoop1:
	addi %x4, 1, %x4					; x4 +=1
	jmp loop1
endProgram:
	end