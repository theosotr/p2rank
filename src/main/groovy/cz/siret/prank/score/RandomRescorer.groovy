package cz.siret.prank.score

import cz.siret.prank.domain.Pocket
import cz.siret.prank.domain.Prediction
import cz.siret.prank.features.api.ProcessedItemContext


class RandomRescorer extends PocketRescorer {

    Random random = new Random(params.seed)

    @Override
    void rescorePockets(Prediction prediction, ProcessedItemContext context) {

        prediction.pockets.each { Pocket pocket ->

            pocket.newScore = random.nextDouble()
        }

    }

}
