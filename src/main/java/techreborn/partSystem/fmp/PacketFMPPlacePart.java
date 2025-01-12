package techreborn.partSystem.fmp;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import reborncore.common.packets.SimplePacket;

public class PacketFMPPlacePart extends SimplePacket {

    public PacketFMPPlacePart() {}

    @Override
    public void writeData(ByteBuf out) throws IOException {

    }

    @Override
    public void readData(ByteBuf in) throws IOException {

    }

    @Override
    public void execute() {
        CableConverter.place(player, player.worldObj);
    }
}
