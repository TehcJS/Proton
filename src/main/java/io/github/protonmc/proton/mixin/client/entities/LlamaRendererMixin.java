package io.github.protonmc.proton.mixin.client.entities;

import io.github.protonmc.proton.module.ModuleManager;
import io.github.protonmc.proton.module.client.VariantAnimalTexturesModule;
import net.minecraft.client.render.entity.LlamaEntityRenderer;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LlamaEntityRenderer.class)
public class LlamaRendererMixin {

    @Shadow @Final private static Identifier[] TEXTURES;

    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    public void getTypeTexture(LlamaEntity llamaEntity, CallbackInfoReturnable<Identifier> cir){
        if(ModuleManager.getInstance().isModuleEnabled(VariantAnimalTexturesModule.class)){
            cir.setReturnValue(VariantAnimalTexturesModule.getTextureOrShiny(llamaEntity, VariantAnimalTexturesModule.VariantTextureType.RABBIT, () -> getOldTexture(llamaEntity)));
        }
    }

    private Identifier getOldTexture(LlamaEntity llamaEntity) {
        return TEXTURES[llamaEntity.getVariant()];
    }
}