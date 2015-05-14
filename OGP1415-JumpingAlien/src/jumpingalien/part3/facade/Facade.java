package jumpingalien.part3.facade;

import java.util.Optional;

import jumpingalien.exception.PositionOutOfBoundsException;
import jumpingalien.model.Buzam;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Program;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.ProgramFactory;
import jumpingalien.part3.programs.ProgramParser;
import jumpingalien.program.internal.Statement;
import jumpingalien.program.internal.Type;
import jumpingalien.program.internal.Value;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public class Facade extends jumpingalien.part2.facade.Facade implements IFacadePart3 {

	@Override
	public Buzam createBuzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites)throws ModelException{
		try{
			// TODO Auto-generated method stub
			return new Buzam(pixelLeftX,pixelBottomY, sprites);
		}catch(PositionOutOfBoundsException e){
			throw new ModelException("illegal possition");
		}
	};

	@Override
	public Buzam createBuzamWithProgram(int pixelLeftX, int pixelBottomY,
			Sprite[] sprites, Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Plant createPlantWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		// TODO Auto-generated method stub
		return createPlant(x, y, sprites);
	}

	@Override
	public Shark createSharkWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		// TODO Auto-generated method stub
		return createShark(x, y, sprites);
	}

	@Override
	public Slime createSlimeWithProgram(int x, int y, Sprite[] sprites,
			School school, Program program) {
		// TODO Auto-generated method stub
		return createSlime(x, y, sprites, school);
	}

	@Override
	public ParseOutcome<?> parse(String text) {
		ProgramFactory factory = new ProgramFactory();
		ProgramParser<Value<?>, Statement, Type, Program> parser = new ProgramParser<>(factory);
		Optional<?> prog= parser.parseString(text);
		if(prog != Optional.empty()){
			return ParseOutcome.success((Program)prog.get());
		}else
			return ParseOutcome.failure(parser.getErrors());
	}

	@Override
	public boolean isWellFormed(Program program) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addBuzam(World world, Buzam buzam) {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getLocation(Buzam alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getVelocity(Buzam alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getAcceleration(Buzam alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getSize(Buzam alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sprite getCurrentSprite(Buzam alien) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNbHitPoints(Buzam alien) {
		// TODO Auto-generated method stub
		return 0;
	}

}
