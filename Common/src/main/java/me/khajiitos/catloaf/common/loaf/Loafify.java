package me.khajiitos.catloaf.common.loaf;

import me.khajiitos.catloaf.common.config.CatLoafConfig;
import net.minecraft.client.model.geom.ModelPart;

public class Loafify {

    public static void loafify(ModelPart leftHindLeg, ModelPart rightHindLeg, ModelPart leftFrontLeg, ModelPart rightFrontLeg, ModelPart tail1, ModelPart tail2, ModelPart head, ModelPart body) {
        // Setting values instead of adding/subtracting would make it cleaner
        // But it works am I right

        body.y = 16.F;
        body.z = -10.0F;
        body.xRot = (float)Math.PI / 2.f;
        body.yRot = 0.f;
        body.zRot = 0.f;

        head.y = 18.0f;

        leftHindLeg.y += 0.2f;
        leftHindLeg.z += 8.25f;

        rightHindLeg.y += 0.2f;
        rightHindLeg.z += 8.25f;

        leftFrontLeg.y = 22.2f;
        leftFrontLeg.z = 2.f;
        leftFrontLeg.xRot = -(float)Math.PI / 2.f;

        rightFrontLeg.y = 22.2f;
        rightFrontLeg.z = 2.f;
        rightFrontLeg.xRot = -(float)Math.PI / 2.f;

        if (CatLoafConfig.hideTailWhenLoafing.get()) {
            // We have to be careful this gets restored
            // when changing the setting or the cat
            // stops loafing
            tail1.visible = false;
            tail2.visible = false;
        } else {
            tail1.z -= 5.f;
            tail1.xRot = (float)Math.PI / 2.f;

            tail2.y += 1.f;
            tail2.z -= 4.f;
            tail2.xRot = (float)Math.PI / 2.f;
        }
    }
}
