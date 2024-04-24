package tech.thatgravyboat.creeperoverhaul.common.entity.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.thatgravyboat.creeperoverhaul.api.PluginRegistry;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

import java.util.EnumSet;
import java.util.List;

public class CreeperAvoidEntitiesGoal extends Goal {

    private final BaseCreeper creeper;
    private final float maxDistance;
    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;

    @Nullable
    private Entity entityToAvoid;
    @Nullable
    private Path path;

    public CreeperAvoidEntitiesGoal(BaseCreeper creeper, float maxDistance, double walkSpeedModifier, double sprintSpeedModifier) {
        this.creeper = creeper;
        this.maxDistance = maxDistance;
        this.walkSpeedModifier = walkSpeedModifier;
        this.sprintSpeedModifier = sprintSpeedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        List<Entity> afraidEntities = this.creeper.level().getEntities(this.creeper, this.creeper.getBoundingBox().inflate(this.maxDistance, 3D, this.maxDistance), this::isAfraidOf);
        this.entityToAvoid = getNearestEntity(afraidEntities, this.creeper);
        if (this.entityToAvoid == null) return false;
        Vec3 posAway = DefaultRandomPos.getPosAway(this.creeper, 16, 7, this.entityToAvoid.position());
        if (posAway == null) return false;
        if (this.entityToAvoid.distanceToSqr(posAway) < this.entityToAvoid.distanceToSqr(this.creeper)) return false;
        this.path = this.creeper.getNavigation().createPath(posAway.x, posAway.y, posAway.z, 0);
        return this.path != null;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.creeper.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.creeper.getNavigation().moveTo(this.path, this.walkSpeedModifier);
    }

    @Override
    public void stop() {
        this.entityToAvoid = null;
    }

    @Override
    public void tick() {
        if (this.entityToAvoid != null) {
            this.creeper.getNavigation().setSpeedModifier(this.creeper.distanceToSqr(this.entityToAvoid) < 49D ? this.sprintSpeedModifier : this.walkSpeedModifier);
        }
    }

    @Nullable
    private Entity getNearestEntity(List<Entity> entities, Entity self){
        if (entities.isEmpty()) return null;
        double lastDist = -1.0D;
        Entity lastEntity = null;
        for (Entity entity : entities) {
            double dist = entity.distanceToSqr(self.getX(), self.getY(), self.getZ());
            if (lastDist == -1.0D || dist < lastDist) {
                lastEntity = entity;
                lastDist = dist;
            }
        }
        return lastEntity;
    }

    public boolean isAfraidOf(Entity entity) {
        if (this.creeper.type.entitiesAfraidOf().contains(entity.getType())) {
            return true;
        }
        if (entity instanceof LivingEntity living) {
            return PluginRegistry.getInstance().isAfraidOf(this.creeper, living);
        }
        return false;
    }
}
