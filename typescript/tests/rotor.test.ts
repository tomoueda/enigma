import {Reflector, Rotor} from '../src/rotor';
import {expect} from 'chai';

describe('Rotor', function() {
    it('errors on bad position', function() { 
        expect(() => {new Rotor('AB', 'I')}).to.throw(Error, 'Invalid Position');
        expect(() => {new Rotor('1', 'I')}).to.throw(Error, 'Invalid Position');
    });

    it('errors on bad rotor types', function() { 
        expect(() => {new Rotor('A', 'ABC')}).to.throw(Error, 'Invalid Rotar Type Input');
        expect(() => {new Rotor('A', '123')}).to.throw(Error, 'Invalid Rotar Type Input');
    });

    it('sets the right offset on initialization', function() {
        const rotor = new Rotor('A', 'VII');
        expect(rotor.offset).to.equal(0);

        const rotor2 = new Rotor('Z', 'VII');
        expect(rotor2.offset).to.equal(25);
    });

    it('advances the offset by 1', function() {
        const rotor = new Rotor('A', 'VII');
        expect(rotor.offset).to.equal(0);
        rotor.advance();
        expect(rotor.offset).to.equal(1);
    });

    it('permutates correctly', function() {
        const rotor = new Rotor('A', 'I');
        expect(rotor.permutate('A')).to.equal('E');
        expect(rotor.permutate('N')).to.equal('W');
        expect(rotor.permutate('S')).to.equal('S');
        expect(rotor.permutate('G')).to.equal('D');

        rotor.advance();

        expect(rotor.permutate('A')).to.equal('J');
        expect(rotor.permutate('N')).to.equal('X');
        expect(rotor.permutate('S')).to.equal('O');
        expect(rotor.permutate('G')).to.equal('P');

        const rotor2 = new Rotor('Z', 'VII');
        expect(rotor2.permutate('A')).to.equal('U');
        rotor2.advance();
        expect(rotor2.permutate('A')).to.equal('N');
    });

    it('reverses correctly', function() {
        const rotor = new Rotor('A', 'I');
        expect(rotor.reverse('E')).to.equal('A');
        expect(rotor.reverse('W')).to.equal('N');
        expect(rotor.reverse('S')).to.equal('S');
        expect(rotor.reverse('D')).to.equal('G');

        rotor.advance();

        expect(rotor.reverse('J')).to.equal('A');
        expect(rotor.reverse('X')).to.equal('N');
        expect(rotor.reverse('O')).to.equal('S');
        expect(rotor.reverse('P')).to.equal('G');

        const rotor2 = new Rotor('Z', 'VII');
        expect(rotor2.reverse('U')).to.equal('A');
        rotor2.advance();
        expect(rotor2.reverse('N')).to.equal('A');
    });


    it('reflector constructor fails on bad inputs', function() {
        expect(() => {new Reflector('AB', 'B')}).to.throw(Error, 'Invalid Position');
        expect(() => {new Reflector('1', 'C')}).to.throw(Error, 'Invalid Position');
        expect(() => {new Reflector('A', 'ABC')}).to.throw(Error, 'Not a viable reflector type');
        expect(() => {new Reflector('A', '123')}).to.throw(Error, 'Not a viable reflector type');
        expect(() => {new Reflector('A', 'VIII')}).to.throw(Error, 'Not a viable reflector type');
        expect(() => {new Reflector('Z', 'II')}).to.throw(Error, 'Not a viable reflector type');
    });

    it('reflector permutates and wont advance', function() {
        const reflector = new Reflector('A', 'B');
        expect(reflector.permutate('G')).to.equal('L');
        expect(reflector.permutate('X')).to.equal('J');

        reflector.advance();

        expect(reflector.permutate('G')).to.equal('L');
        expect(reflector.permutate('X')).to.equal('J');
    });

    it('determines the if place is in notch', function() {
        const rotor = new Rotor('Q', 'I');
        expect(rotor.isInNotch()).to.be.true;
        rotor.advance();
        expect(rotor.isInNotch()).to.be.false;

        const rotor2 = new Rotor('M', 'VIII');
        expect(rotor2.isInNotch()).to.be.true;

        const rotor3 = new Rotor('Z', 'VIII');
        expect(rotor3.isInNotch()).to.be.true;
    });
});
