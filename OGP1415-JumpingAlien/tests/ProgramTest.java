import static org.junit.Assert.*;

import org.junit.Test;

import jumpingalien.model.Program;
import jumpingalien.model.Shark;
import jumpingalien.model.World;
import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.facade.IFacadePart3;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.tests.util.TestUtils;
import jumpingalien.util.Util;


public class ProgramTest {

	@Test
	public void testProgram() {//test for no parsing errors on every statement and expression
		IFacadePart3 facade = new Facade();
		ParseOutcome<?> outcome = facade.parse("double d1 := 1;\n"
				+ "double d2;\n"
				+ "object o;\n"
				+ "direction dir := left;\n"
				+ "direction dir2;\n"
				+ "bool b;\n"
				+ "bool b2 := true;\n"
				+ "dir := right;dir:=up;dir:=down;\n"
				+ "dir2 := dir;\n"
				+ "d2 :=d1;\n"
				+ "d2 := (d1);\n"
				+ "b := false;\n"
				+ "b := b2;\n"
				+ "print 1 + 2;\n"
				+ "print 1 - 2;\n"
				+ "print 1*2;\n"
				+ "print 1/2;\n"
				+ "print sqrt(1);print sqrt(4);\n"
				+ "print true && false;\n"
				+ "print false && null;\n"
				+ "print true ||false;\n"
				+ "print true || null;\n"
				+ "print false || (true && false);\n"
				+ "print ! true;\n"
				+ "print 1<2;\n"
				+ "print d1<2;\n"
				+ "print 1<=1;\n"
				+ "print 1>1;\n"
				+ "print 1>=1;\n"
				+ "print 1 == 1;\n"
				+ "print 1 != 1;\n"
				+ "foreach (slime,o) where true sort gethp o descending do"
				+ "    print self == o;\n"
				+ "    print getx o;\n"
				+ "    print gety o;\n"
				+ "    print getwidth o;\n"
				+ "    print getheight o;\n"
				+ "    print gethp o;\n"
				+ "    print searchobj dir;\n"
				+ "    print ismazub o;\n"
				+ "    print isshark o;\n"
				+ "    print isslime o;\n"
				+ "    print isplant o;\n"
				+ "    print isdead o;\n"
				+ "    print isterrain o;\n"
				+ "    print ispassable o;\n"
				+ "    print iswater o;\n"
				+ "    print ismagma o;\n"
				+ "    print isair o;\n"
				+ "    print ismoving (o,left);\n"
				+ "    print isducking o;\n"
				+ "    print isjumping o;\n"
				+ "    print gettile(1,1);\n"
				+ "	   break;"
				+ "done\n"
				+ "skip;\n"
				+ "while 1<2 do\n"
				+ "    break;\n"
				+ "done\n"
				+ "if b then\n"
				+ "    print true;\n"
				+ "else\n"
				+ "    print false;\n"
				+ "fi\n"
				+ "wait d1;\n"
				+ "wait 1;\n"
				+ "if b then print true; fi");
		assertTrue(outcome.isSuccess());
		Program program = (Program) outcome.getResult();
		World world = facade.createWorld(1, 1, 1, 1, 1, 0, 0);
		Shark shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertFalse(program.hasError());//there is no slime, so no error for ispassable on slime
	}

	@Test
	public void testHasError() {
		IFacadePart3 facade = new Facade();
		ParseOutcome<?> outcome = facade.parse(""
				+ "double o:= 1; print gethp o;");
		assertTrue(outcome.isSuccess());
		Program program = (Program) outcome.getResult();
		World world = facade.createWorld(1, 1, 1, 1, 1, 0, 0);
		Shark shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("object o;\n"
		 		+ "o := gettile (0.0,0.0);\n"
				+ "print gethp o;\n");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("object o;\n"
		 		+ "o := gettile (0.0,0.0);\n"
				+ "print getwidth o;\n");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("object o;\n"
		 		+ "o := gettile (0.0,0.0);\n"
				+ "print getheight o;\n");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("object o;\n"
		 		+ "o := gettile (0.0,0.0);\n"
				+ "print ismoving (o,left);\n");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("object o;\n"
		 		+ "o := gettile (0.0,0.0);\n"
				+ "print isjumping o;\n");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("object o;\n"
		 		+ "break;");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("object o;\n"
		 		+ "if true then break; fi");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(1, 1, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("object o;\n"
				+ "print gethp self;\n");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(0.01d);
		assertFalse(program.hasError());
		
		outcome = facade.parse("object o;\n"
				+ "print isjumping null;\n");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("print ispassable self;");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("print isair self;");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("print iswater self;");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("print ismagma self;");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("print 5 + true;");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("print 5 == self;");//comparison is allowed between different kinds
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(0.001d);
		assertFalse(program.hasError());
		
		outcome = facade.parse("double o; o:=self;");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("double o; o:=null;");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("bool o; o:=null;");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("direction o; o:=null;");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertTrue(program.hasError());
		
		outcome = facade.parse("object o; o:=null;");
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		program.executeTime(5.0d);
		assertFalse(program.hasError());//object is allowed to have a null value.
		
		//non-existing variables are forbidden by the parser.
	}
	
	@Test
	public void testExecuteTime() {//the easiest way to time this is evaluating when an error occurs.
		IFacadePart3 facade = new Facade();
		ParseOutcome<?> outcome = facade.parse("double t := 0;"
				+ "while true do\n"
				+ "    t := t+1;\n"
				+ "    if t>100 then\n"
				+ "        break;\n"
				+ "    fi\n"
				+ "done\n"
				+ "print ismagma self;  ");
		assertTrue(outcome.isSuccess());
		Program program = (Program) outcome.getResult();
		World world = facade.createWorld(1, 1, 1, 1, 1, 0, 0);
		Shark shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		for(int i=0;i<1010;i++){
			program.executeTime(0.001);
			if(i<305)//after 306 time consumptions, the print statement that will error, is executed
				assertFalse(program.hasError());
			else
				assertTrue(program.hasError());
		}
		assertTrue(program.hasError());
		
		
		outcome = facade.parse("object o;"
				+ "foreach (shark,o) where (! isdead o) sort gethp o ascending do\n"
				+ "    skip;\n"
				+ "done\n"
				+ "print ismagma self;  ");
		assertTrue(outcome.isSuccess());
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);//there are 2 sharks now
		for(int i=0;i<50;i++){
			program.executeTime(0.001);
			if(i<3)//1 for foreach, 2 for skip, 4th iteration (i=3)should be print
				assertFalse(program.hasError());
			else
				assertTrue(program.hasError());
		}
		assertTrue(program.hasError());
		
		
		outcome = facade.parse(""
				+ "wait 10;"
				+ "print ismagma self;  ");
		assertTrue(outcome.isSuccess());
		program = (Program) outcome.getResult();
		shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);//there are 2 sharks now
		for(int i=0;i<100;i++){
			program.executeTime(0.1d);
			assertFalse(program.hasError());
		}
		program.executeTime(0.001d);
		assertTrue(program.hasError());
	}

	@Test
	public void testResetGlobals() {
		IFacadePart3 facade = new Facade();
		ParseOutcome<?> outcome = facade.parse("double t := 0;direction d;object o;bool b;"
				+ "b:=false;"
				+ "o:=self;"
				+ "d:=left;"
				+ "while true do\n"
				+ "    t := t+1;\n"
				+ "done\n");
		assertTrue(outcome.isSuccess());
		Program program = (Program) outcome.getResult();
		World world = facade.createWorld(1, 1, 1, 1, 1, 0, 0);
		Shark shark = facade.createSharkWithProgram(0, 0, TestUtils.spriteArrayForSize(10, 10, 2),program);
		facade.addShark(world, shark);
		for(int i=0;i<100;i++){
			program.executeTime(0.001);
			if(i>=3){
				assertEquals((double)((i-3)/2), (double)program.getVariable("t").evaluate(new double[]{1}),Util.DEFAULT_EPSILON);
				assertEquals(shark,program.getVariable("o").evaluate(new double[]{1}));
				assertEquals(false,program.getVariable("b").evaluate(new double[]{1}));
				assertEquals(Program.Direction.LEFT,program.getVariable("d").evaluate(new double[]{1}));
			}
		}
		program.resetGlobals();
		assertNull(program.getVariable("t").evaluate(new double[]{1}));
		assertNull(program.getVariable("b").evaluate(new double[]{1}));
		assertNull(program.getVariable("d").evaluate(new double[]{1}));
		assertNull(program.getVariable("o").evaluate(new double[]{1}));
	}
	
	@Test
	public void testIsWellFormed() {
		IFacadePart3 facade = new Facade();
		ParseOutcome<?> outcome = facade.parse("break;");
		assertTrue(outcome.isSuccess());
		Program program = (Program) outcome.getResult();
		assertFalse(program.isWellFormed());
		
		outcome = facade.parse("if true then break; fi");
		assertTrue(outcome.isSuccess());
		program = (Program) outcome.getResult();
		assertFalse(program.isWellFormed());
		
		outcome = facade.parse("while true do skip; done break;");
		assertTrue(outcome.isSuccess());
		program = (Program) outcome.getResult();
		assertFalse(program.isWellFormed());
		
		outcome = facade.parse("while true do while true do skip; done break; done");
		assertTrue(outcome.isSuccess());
		program = (Program) outcome.getResult();
		assertTrue(program.isWellFormed());
		
		outcome = facade.parse("while true do if true then break; fi done");
		assertTrue(outcome.isSuccess());
		program = (Program) outcome.getResult();
		assertTrue(program.isWellFormed());
		
		outcome = facade.parse("object o; foreach (mazub, o) do if true then break; else break; fi break; done");//multiple breaks inside 1 loop should be allowed
		assertTrue(outcome.isSuccess());
		program = (Program) outcome.getResult();
		assertTrue(program.isWellFormed());
		
		outcome = facade.parse("while true do break; skip; done"); // unreachable code
		assertTrue(outcome.isSuccess());
		program = (Program) outcome.getResult();
		assertFalse(program.isWellFormed());
		
		outcome = facade.parse("while true do if true then break; else skip; fi done");
		assertTrue(outcome.isSuccess());
		program = (Program) outcome.getResult();
		assertTrue(program.isWellFormed());
		
		outcome = facade.parse("object o; foreach (mazub,o) do skip; done break;");
		assertTrue(outcome.isSuccess());
		program = (Program) outcome.getResult();
		assertFalse(program.isWellFormed());
	}
}
