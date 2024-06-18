import android.os.Parcel
import android.os.Parcelable

data class ConsultationData(
    val name: String,
    val jenis_kelamin: String,
    val umur: Float,
    val bb: Float,
    val pb: Float,
    val lk: Float
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(jenis_kelamin)
        parcel.writeFloat(umur)
        parcel.writeFloat(bb)
        parcel.writeFloat(pb)
        parcel.writeFloat(lk)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ConsultationData> {
        override fun createFromParcel(parcel: Parcel): ConsultationData {
            return ConsultationData(parcel)
        }

        override fun newArray(size: Int): Array<ConsultationData?> {
            return arrayOfNulls(size)
        }
    }
}

data class PredictionResponse(
    val status: String,
    val data: ConsultationResult?
)

data class ConsultationResult(
    val userID: String,
    val name: String,
    val umur: Float,
    val jenis_kelamin: String,
    val bb: Float = 0f,
    val pb: Float = 0f,
    val lk: Float = 0f,
    val label: String,
    val ideal: String,
    val suggestion: String,
    val supplement_url: String
)

data class CustomResult(
    val name: String,
    val gender: String,
    val age: Float,
    val weight: Float,
    val headCircumference: Float,
    val height: Float
)