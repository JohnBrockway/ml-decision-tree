/**
 * A class to hold all information relating to a single case; all of the field values as well as whether this case is healthy
 * @author John Brockway
 */
public class Case {
	double k;
	double na;
	double cl;
	double hco3;
	double endotoxin;
	double aniongap;
	double pla2;
	double sdh;
	double gldh;
	double tpp;
	double breathRate;
	double pcv;
	double pulseRate;
	double fibrinogen;
	double dimer;
	double fibPerDim;
	boolean isHealthy;

	public Case (double k, double na, double cl, double hco3, double endotoxin, double aniongap, double pla2, double sdh, double gldh, double tpp, double breathRate, double pcv, double pulseRate, double fibrinogen, double dimer, double fibPerDim, boolean isHealthy) {
		this.k = k;
		this.na = na;
		this.cl = cl;
		this.hco3 = hco3;
		this.endotoxin = endotoxin;
		this.aniongap = aniongap;
		this.pla2 = pla2;
		this.sdh = sdh;
		this.gldh = gldh;
		this.tpp = tpp;
		this.breathRate = breathRate;
		this.pcv = pcv;
		this.pulseRate = pulseRate;
		this.fibrinogen = fibrinogen;
		this.dimer = dimer;
		this.fibPerDim = fibPerDim;
		this.isHealthy = isHealthy;
	}
}