package bachelor.test.locationapp.presenter.positioning

import org.apache.commons.math3.filter.DefaultMeasurementModel
import org.apache.commons.math3.filter.DefaultProcessModel
import org.apache.commons.math3.filter.KalmanFilter
import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.apache.commons.math3.linear.ArrayRealVector

private const val TIME_DELTA = 0.1

class KalmanFilterConfigurator {
    private val stateVector = ArrayRealVector(6)
    private val stateTransitionMatrix = Array2DRowRealMatrix(6, 6)
    private val controlMatrix = Array2DRowRealMatrix(6, 3)
    private val processNoiseMatrix = Array2DRowRealMatrix(6, 6)
    private val stateEstimateNoiseMatrix = Array2DRowRealMatrix(6, 6)
    private val measurementMatrix = Array2DRowRealMatrix(3, 6)
    private val measurementNoiseMatrix = Array2DRowRealMatrix(3, 3)

    private lateinit var processModel: DefaultProcessModel
    private lateinit var measurementModel: DefaultMeasurementModel

    fun configureKalmanFilter(initialLocation: LocationData): KalmanFilter {
        processModel = configureProcessModel(initialLocation)
        measurementModel = configureMeasurementModel()
        return KalmanFilter(processModel, measurementModel)
    }

    private fun configureProcessModel(initialLocation: LocationData): DefaultProcessModel {
        stateVector.setEntry(0, initialLocation.xPos.toDouble())
        stateVector.setEntry(1, initialLocation.yPos.toDouble())
        stateVector.setEntry(2, initialLocation.zPos.toDouble())
        stateVector.setEntry(3, 0.0)
        stateVector.setEntry(4, 0.0)
        stateVector.setEntry(5, 0.0)

        stateEstimateNoiseMatrix.setEntry(0, 0, 1.0)
        stateEstimateNoiseMatrix.setEntry(0, 1, 0.0)
        stateEstimateNoiseMatrix.setEntry(0, 2, 0.0)
        stateEstimateNoiseMatrix.setEntry(0, 3, 0.0)
        stateEstimateNoiseMatrix.setEntry(0, 4, 0.0)
        stateEstimateNoiseMatrix.setEntry(0, 5, 0.0)
        stateEstimateNoiseMatrix.setEntry(1, 0, 0.0)
        stateEstimateNoiseMatrix.setEntry(1, 1, 1.0)
        stateEstimateNoiseMatrix.setEntry(1, 2, 0.0)
        stateEstimateNoiseMatrix.setEntry(1, 3, 0.0)
        stateEstimateNoiseMatrix.setEntry(1, 4, 0.0)
        stateEstimateNoiseMatrix.setEntry(1, 5, 0.0)
        stateEstimateNoiseMatrix.setEntry(2, 0, 0.0)
        stateEstimateNoiseMatrix.setEntry(2, 1, 0.0)
        stateEstimateNoiseMatrix.setEntry(2, 2, 1.0)
        stateEstimateNoiseMatrix.setEntry(2, 3, 0.0)
        stateEstimateNoiseMatrix.setEntry(2, 4, 0.0)
        stateEstimateNoiseMatrix.setEntry(2, 5, 0.0)
        stateEstimateNoiseMatrix.setEntry(3, 0, 0.0)
        stateEstimateNoiseMatrix.setEntry(3, 1, 0.0)
        stateEstimateNoiseMatrix.setEntry(3, 2, 0.0)
        stateEstimateNoiseMatrix.setEntry(3, 3, 0.1)
        stateEstimateNoiseMatrix.setEntry(3, 4, 0.0)
        stateEstimateNoiseMatrix.setEntry(3, 5, 0.0)
        stateEstimateNoiseMatrix.setEntry(4, 0, 0.0)
        stateEstimateNoiseMatrix.setEntry(4, 1, 0.0)
        stateEstimateNoiseMatrix.setEntry(4, 2, 0.0)
        stateEstimateNoiseMatrix.setEntry(4, 3, 0.0)
        stateEstimateNoiseMatrix.setEntry(4, 4, 0.1)
        stateEstimateNoiseMatrix.setEntry(4, 5, 0.0)
        stateEstimateNoiseMatrix.setEntry(5, 0, 0.0)
        stateEstimateNoiseMatrix.setEntry(5, 1, 0.0)
        stateEstimateNoiseMatrix.setEntry(5, 2, 0.0)
        stateEstimateNoiseMatrix.setEntry(5, 3, 0.0)
        stateEstimateNoiseMatrix.setEntry(5, 4, 0.0)
        stateEstimateNoiseMatrix.setEntry(5, 5, 0.1)

        stateTransitionMatrix.setEntry(0, 0, 1.0)
        stateTransitionMatrix.setEntry(0, 1, 0.0)
        stateTransitionMatrix.setEntry(0, 2, 0.0)
        stateTransitionMatrix.setEntry(0, 3, TIME_DELTA)
        stateTransitionMatrix.setEntry(0, 4, 0.0)
        stateTransitionMatrix.setEntry(0, 5, 0.0)
        stateTransitionMatrix.setEntry(1, 0, 0.0)
        stateTransitionMatrix.setEntry(1, 1, 1.0)
        stateTransitionMatrix.setEntry(1, 2, 0.0)
        stateTransitionMatrix.setEntry(1, 3, 0.0)
        stateTransitionMatrix.setEntry(1, 4, TIME_DELTA)
        stateTransitionMatrix.setEntry(1, 5, 0.0)
        stateTransitionMatrix.setEntry(2, 0, 0.0)
        stateTransitionMatrix.setEntry(2, 1, 0.0)
        stateTransitionMatrix.setEntry(2, 2, 1.0)
        stateTransitionMatrix.setEntry(2, 3, 0.0)
        stateTransitionMatrix.setEntry(2, 4, 0.0)
        stateTransitionMatrix.setEntry(2, 5, TIME_DELTA)
        stateTransitionMatrix.setEntry(3, 0, 0.0)
        stateTransitionMatrix.setEntry(3, 1, 0.0)
        stateTransitionMatrix.setEntry(3, 2, 0.0)
        stateTransitionMatrix.setEntry(3, 3, 1.0)
        stateTransitionMatrix.setEntry(3, 4, 0.0)
        stateTransitionMatrix.setEntry(3, 5, 0.0)
        stateTransitionMatrix.setEntry(4, 0, 0.0)
        stateTransitionMatrix.setEntry(4, 1, 0.0)
        stateTransitionMatrix.setEntry(4, 2, 0.0)
        stateTransitionMatrix.setEntry(4, 3, 0.0)
        stateTransitionMatrix.setEntry(4, 4, 1.0)
        stateTransitionMatrix.setEntry(4, 5, 0.0)
        stateTransitionMatrix.setEntry(5, 0, 0.0)
        stateTransitionMatrix.setEntry(5, 1, 0.0)
        stateTransitionMatrix.setEntry(5, 2, 0.0)
        stateTransitionMatrix.setEntry(5, 3, 0.0)
        stateTransitionMatrix.setEntry(5, 4, 0.0)
        stateTransitionMatrix.setEntry(5, 5, 1.0)

        controlMatrix.setEntry(0, 0, 0.5 * (TIME_DELTA * TIME_DELTA))
        controlMatrix.setEntry(0, 1, 0.0)
        controlMatrix.setEntry(0, 2, 0.0)
        controlMatrix.setEntry(1, 0, 0.0)
        controlMatrix.setEntry(1, 1, 0.5 * (TIME_DELTA * TIME_DELTA))
        controlMatrix.setEntry(1, 2, 0.0)
        controlMatrix.setEntry(2, 0, 0.0)
        controlMatrix.setEntry(2, 1, 0.0)
        controlMatrix.setEntry(2, 2, 0.5 * (TIME_DELTA * TIME_DELTA))
        controlMatrix.setEntry(3, 0, TIME_DELTA)
        controlMatrix.setEntry(3, 1, 0.0)
        controlMatrix.setEntry(3, 2, 0.0)
        controlMatrix.setEntry(4, 0, 0.0)
        controlMatrix.setEntry(4, 1, TIME_DELTA)
        controlMatrix.setEntry(4, 2, 0.0)
        controlMatrix.setEntry(5, 0, 0.0)
        controlMatrix.setEntry(5, 1, 0.0)
        controlMatrix.setEntry(5, 2, TIME_DELTA)

        processNoiseMatrix.setEntry(0, 0, 0.0)
        processNoiseMatrix.setEntry(0, 1, 0.0)
        processNoiseMatrix.setEntry(0, 2, 0.0)
        processNoiseMatrix.setEntry(0, 3, 0.0)
        processNoiseMatrix.setEntry(0, 4, 0.0)
        processNoiseMatrix.setEntry(0, 5, 0.0)
        processNoiseMatrix.setEntry(1, 0, 0.0)
        processNoiseMatrix.setEntry(1, 1, 0.0)
        processNoiseMatrix.setEntry(1, 2, 0.0)
        processNoiseMatrix.setEntry(1, 3, 0.0)
        processNoiseMatrix.setEntry(1, 4, 0.0)
        processNoiseMatrix.setEntry(1, 5, 0.0)
        processNoiseMatrix.setEntry(2, 0, 0.0)
        processNoiseMatrix.setEntry(2, 1, 0.0)
        processNoiseMatrix.setEntry(2, 2, 0.0)
        processNoiseMatrix.setEntry(2, 3, 0.0)
        processNoiseMatrix.setEntry(2, 4, 0.0)
        processNoiseMatrix.setEntry(2, 5, 0.0)
        processNoiseMatrix.setEntry(3, 0, 0.0)
        processNoiseMatrix.setEntry(3, 1, 0.0)
        processNoiseMatrix.setEntry(3, 2, 0.0)
        processNoiseMatrix.setEntry(3, 3, 0.007)
        processNoiseMatrix.setEntry(3, 4, 0.0)
        processNoiseMatrix.setEntry(3, 5, 0.0)
        processNoiseMatrix.setEntry(4, 0, 0.0)
        processNoiseMatrix.setEntry(4, 1, 0.0)
        processNoiseMatrix.setEntry(4, 2, 0.0)
        processNoiseMatrix.setEntry(4, 3, 0.0)
        processNoiseMatrix.setEntry(4, 4, 0.007)
        processNoiseMatrix.setEntry(4, 5, 0.0)
        processNoiseMatrix.setEntry(5, 0, 0.0)
        processNoiseMatrix.setEntry(5, 1, 0.0)
        processNoiseMatrix.setEntry(5, 2, 0.0)
        processNoiseMatrix.setEntry(5, 3, 0.0)
        processNoiseMatrix.setEntry(5, 4, 0.0)
        processNoiseMatrix.setEntry(5, 5, 0.007)

        // Alternative Q
        processNoiseMatrix.setEntry(0, 0, 0.000025)
        processNoiseMatrix.setEntry(0, 1, 0.0)
        processNoiseMatrix.setEntry(0, 2, 0.0)
        processNoiseMatrix.setEntry(0, 3, 0.0005)
        processNoiseMatrix.setEntry(0, 4, 0.0)
        processNoiseMatrix.setEntry(0, 5, 0.0)
        processNoiseMatrix.setEntry(1, 0, 0.0)
        processNoiseMatrix.setEntry(1, 1, 0.000025)
        processNoiseMatrix.setEntry(1, 2, 0.0)
        processNoiseMatrix.setEntry(1, 3, 0.0)
        processNoiseMatrix.setEntry(1, 4, 0.0005)
        processNoiseMatrix.setEntry(1, 5, 0.0)
        processNoiseMatrix.setEntry(2, 0, 0.0)
        processNoiseMatrix.setEntry(2, 1, 0.0)
        processNoiseMatrix.setEntry(2, 2, 0.000025)
        processNoiseMatrix.setEntry(2, 3, 0.0)
        processNoiseMatrix.setEntry(2, 4, 0.0)
        processNoiseMatrix.setEntry(2, 5, 0.0005)
        processNoiseMatrix.setEntry(3, 0, 0.0005)
        processNoiseMatrix.setEntry(3, 1, 0.0)
        processNoiseMatrix.setEntry(3, 2, 0.0)
        processNoiseMatrix.setEntry(3, 3, 0.01)
        processNoiseMatrix.setEntry(3, 4, 0.0)
        processNoiseMatrix.setEntry(3, 5, 0.0)
        processNoiseMatrix.setEntry(4, 0, 0.0)
        processNoiseMatrix.setEntry(4, 1, 0.0005)
        processNoiseMatrix.setEntry(4, 2, 0.0)
        processNoiseMatrix.setEntry(4, 3, 0.0)
        processNoiseMatrix.setEntry(4, 4, 0.01)
        processNoiseMatrix.setEntry(4, 5, 0.0)
        processNoiseMatrix.setEntry(5, 0, 0.0)
        processNoiseMatrix.setEntry(5, 1, 0.0)
        processNoiseMatrix.setEntry(5, 2, 0.0005)
        processNoiseMatrix.setEntry(5, 3, 0.0)
        processNoiseMatrix.setEntry(5, 4, 0.0)
        processNoiseMatrix.setEntry(5, 5, 0.01)

        return DefaultProcessModel(stateTransitionMatrix, controlMatrix, processNoiseMatrix, stateVector, stateEstimateNoiseMatrix)
    }

    private fun configureMeasurementModel(): DefaultMeasurementModel {
        measurementMatrix.setEntry(0, 0, 1.0)
        measurementMatrix.setEntry(0, 1, 0.0)
        measurementMatrix.setEntry(0, 2, 0.0)
        measurementMatrix.setEntry(0, 3, 0.0)
        measurementMatrix.setEntry(0, 4, 0.0)
        measurementMatrix.setEntry(0, 5, 0.0)
        measurementMatrix.setEntry(1, 0, 0.0)
        measurementMatrix.setEntry(1, 1, 1.0)
        measurementMatrix.setEntry(1, 2, 0.0)
        measurementMatrix.setEntry(1, 3, 0.0)
        measurementMatrix.setEntry(1, 4, 0.0)
        measurementMatrix.setEntry(1, 5, 0.0)
        measurementMatrix.setEntry(2, 0, 0.0)
        measurementMatrix.setEntry(2, 1, 0.0)
        measurementMatrix.setEntry(2, 2, 1.0)
        measurementMatrix.setEntry(2, 3, 0.0)
        measurementMatrix.setEntry(2, 4, 0.0)
        measurementMatrix.setEntry(2, 5, 0.0)

        measurementNoiseMatrix.setEntry(0, 0, 0.1)
        measurementNoiseMatrix.setEntry(0, 1, 0.0)
        measurementNoiseMatrix.setEntry(0, 2, 0.0)
        measurementNoiseMatrix.setEntry(1, 0, 0.0)
        measurementNoiseMatrix.setEntry(1, 1, 0.1)
        measurementNoiseMatrix.setEntry(1, 2, 0.0)
        measurementNoiseMatrix.setEntry(2, 0, 0.0)
        measurementNoiseMatrix.setEntry(2, 1, 0.0)
        measurementNoiseMatrix.setEntry(2, 2, 0.2)

        // Alternative R
        measurementNoiseMatrix.setEntry(0, 0, 0.005)
        measurementNoiseMatrix.setEntry(0, 1, 0.0023)
        measurementNoiseMatrix.setEntry(0, 2, 0.0018)
        measurementNoiseMatrix.setEntry(1, 0, 0.0023)
        measurementNoiseMatrix.setEntry(1, 1, 0.0137)
        measurementNoiseMatrix.setEntry(1, 2, 0.0036)
        measurementNoiseMatrix.setEntry(2, 0, 0.0018)
        measurementNoiseMatrix.setEntry(2, 1, 0.0036)
        measurementNoiseMatrix.setEntry(2, 2, 0.029)

        return DefaultMeasurementModel(measurementMatrix, measurementNoiseMatrix)
    }

}