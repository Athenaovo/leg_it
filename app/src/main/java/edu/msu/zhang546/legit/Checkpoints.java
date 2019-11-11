package edu.msu.zhang546.legit;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class that selects a random checkpoint.
 */
class Checkpoints {

    private final Random random = new Random();

    /**
     * The currently selected location
     */
    private Checkpoint checkpoint = null;

    static class Checkpoint {

        Checkpoint(String n, double lat, double lon) {
            name = n;
            latitude = lat;
            longitude = lon;
        }

        final String name;
        final double latitude;
        final double longitude;
    }

    public Checkpoints() { choose();}

    /**
     * Choose a new checkpoint
     */
    public final void choose() {
        int maxLoc = locations.size();

        checkpoint = locations.get(random.nextInt(maxLoc));
    }

    /**
     * Get the name of the current checkpoint.
     *
     * @return Current name.
     */
    public String getName() {
        return checkpoint.name;
    }

    /**
     * Get the latitude of the current checkpoint.
     *
     * @return Current latitude
     */
    public double getLatitude() {
        return checkpoint.latitude;
    }

    /**
     * Get the longitude of the current checkpoint.
     *
     * @return Current longitude
     */
    public double getLongitude() {
        return checkpoint.longitude;
    }

    public void setLocation(String name) {
        for (Checkpoint loc : locations) {
            if (name.equals(loc.name)) {
                checkpoint = loc;
                return;
            }
        }
    }

    /**
     * Collection of checkpoints
     */
    private static final ArrayList<Checkpoint> locations = new ArrayList<Checkpoint>() {{
        // A
        add(new Checkpoint("Abrams Planetarium", 42.725630, -84.475883));
        add(new Checkpoint("Agriculture Hall", 42.731086, -84.479380));
        add(new Checkpoint("Alumni Memorial Chapel", 42.728434, -84.474003));
        add(new Checkpoint("Anthony Hall", 42.724850, -84.479020));
        add(new Checkpoint("MSU Auditorium Building", 42.728782, -84.476997));

        // B
        add(new Checkpoint("Beal Botanical Garden", 42.731188, -84.484832));
        add(new Checkpoint("Beaumont Tower", 42.731955, -84.482239));
        add(new Checkpoint("Benefactors Plaza", 42.731755, -84.477842));
        add(new Checkpoint("Berkey Hall", 42.732865, -84.478215));
        add(new Checkpoint("Bessey Hall", 42.728534, -84.478371));
        add(new Checkpoint("Biomedical and Physical Sciences Building", 42.723943, -84.476232));
        add(new Checkpoint("Breslin Student Events Center", 42.728435, -84.492584));
        add(new Checkpoint("Broad Art Museum", 42.732789, -84.476696));
        add(new Checkpoint("Business College Complex", 42.726941, -84.472839));

        // C
        add(new Checkpoint("Chemistry Building", 42.724902, -84.475612));
        add(new Checkpoint("Chittenden Hall", 42.731792, -84.479369));
        add(new Checkpoint("Communication Arts and Sciences Building", 42.722506, -84.481432));
        add(new Checkpoint("Computer Center", 42.729162, -84.480379));
        add(new Checkpoint("Cook Hall", 42.731513, -84.479535));

        /// D
        add(new Checkpoint("MSU Dairy Store", 42.724182, -84.478503));
        add(new Checkpoint("Demonstration Hall", 42.729578, -84.488693));

        // E
        add(new Checkpoint("Engineering Building", 42.725204, -84.481292));
        add(new Checkpoint("Erickson Hall", 42.727000, -84.479504));
        add(new Checkpoint("Eustace-Cole Hall", 42.732578, -84.479600));

        // F
        add(new Checkpoint("Farrall Agricultural Engineering", 42.724794, -84.477252));
        add(new Checkpoint("Food Safety and Toxicology Building", 42.721128, -84.474904));

        // G
        add(new Checkpoint("Geography Building", 42.729303, -84.472959));
        add(new Checkpoint("Giltner Hall", 42.730197, -84.476452));

        // H
        add(new Checkpoint("Hannah Administration Building", 42.729616, -84.481491));
        add(new Checkpoint("Horticultural Demonstration Gardens", 42.722102, -84.474531));
        add(new Checkpoint("Human Ecology Building", 42.733925, -84.481574));

        // I
        add(new Checkpoint("IM Sports Circle", 42.731747, -84.485865));
        add(new Checkpoint("IM Sports West", 42.729167, -84.487466));
        add(new Checkpoint("International Center", 42.726601, -84.480711));

        // K
        add(new Checkpoint("Kellogg Hotel and Conference Center", 42.731662, -84.493285));
        add(new Checkpoint("Kresge Art Center", 42.728422, -84.474669));

        // L
        add(new Checkpoint("Linton Hall", 42.732097, -84.480406));

        // M
        add(new Checkpoint("Main Library", 42.730927, -84.483166));
        add(new Checkpoint("MSU College of Law", 42.725747, -84.473575));
        add(new Checkpoint("MSU Museum", 42.731566, -84.481715));
        add(new Checkpoint("MSU Union", 42.734191, -84.482876));
        add(new Checkpoint("Music Building", 42.732254, -84.484319));

        // N
        add(new Checkpoint("Natural Resources Building", 42.722568, -84.478499));
        add(new Checkpoint("Natural Science Building", 42.731109, -84.476867));
        add(new Checkpoint("North Kedzie Hall", 42.729846, -84.478944));

        // O
        add(new Checkpoint("Old Horticulture Building", 42.732286, -84.478211));
        add(new Checkpoint("Olds Hall", 42.730488, -84.481715));
        add(new Checkpoint("Olin Health Center", 42.733361, -84.479239));

        // P
        add(new Checkpoint("Packaging Building", 42.722709, -84.480118));
        add(new Checkpoint("Plant and Soil Sciences Building", 42.722512, -84.472903));
        add(new Checkpoint("Psychology Building", 42.730597, -84.475321));

        // S
        add(new Checkpoint("South Kedzie Hall", 42.729595, -84.478364));
        add(new Checkpoint("Spartan Statue", 42.731130, -84.487478));
        add(new Checkpoint("Student Services Building", 42.732015, -84.476265));

        // T
        add(new Checkpoint("The Rock", 42.728140, -84.477519));

        // U
        add(new Checkpoint("Urban Planning and Landscape Architecture Building", 42.723607, -84.483018));

        // W
        add(new Checkpoint("Wells Hall", 42.727408, -84.482014));
    }};
}