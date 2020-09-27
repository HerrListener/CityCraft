package de.citycraft.plot;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
public class BlockPlot {

    private Material material;
    private byte data;
    private Location location;


}
