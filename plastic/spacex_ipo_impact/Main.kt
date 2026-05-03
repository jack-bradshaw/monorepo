package spacex_ipo_impact

data class CompanyData(
    val ticker: String,
    val marketCap: Long,
    val price: Double,
    val adtvShares: Long
) {
    val dailyDollarVolume: Double = price * adtvShares
}

fun main() {
    // Inputs (Variables we specified)
    val spacexTotalValuation = 1_500_000_000_000L // $1.5 Trillion base valuation
    val freeFloatAdjustment = 0.05 // 5% float
    val spacexFloatAdjustedValuation = (spacexTotalValuation * freeFloatAdjustment).toLong()
    
    val totalPassiveAum = 300_000_000_000L // $300B tracking the index
    val prePositioningFactor = 0.50 // Hedge funds have already priced in 50% of the dip
    
    // Total Nasdaq-100 Market Cap (Approximate)
    val indexTotalMarketCap = 25_000_000_000_000L
    val newIndexTotalMarketCap = indexTotalMarketCap + spacexFloatAdjustedValuation
    
    val spacexWeight = spacexFloatAdjustedValuation.toDouble() / newIndexTotalMarketCap

    // Hardcoded sample of realistic data for bottom-tier N100 companies and one mega-cap (AAPL) for comparison
    val marketData = listOf(
        CompanyData("AAPL", 2_800_000_000_000L, 180.0, 60_000_000L),
        CompanyData("MRNA", 40_000_000_000L, 105.0, 4_000_000L),
        CompanyData("ZS", 28_000_000_000L, 180.0, 2_500_000L),
        CompanyData("DLTR", 25_000_000_000L, 115.0, 2_000_000L),
        CompanyData("ILMN", 22_000_000_000L, 130.0, 2_000_000L),
        CompanyData("WBD", 20_000_000_000L, 8.0, 20_000_000L),
        CompanyData("WBA", 18_000_000_000L, 21.0, 10_000_000L)
    )

    println("=== SpaceX IPO Impact Analysis ===")
    println("SpaceX Float-Adjusted Valuation: \$${spacexFloatAdjustedValuation / 1_000_000_000} Billion")
    println("SpaceX Target Weight in Index: ${String.format("%.2f", spacexWeight * 100)}%")
    println("Total Forced Selling (Nasdaq-100 only): \$${String.format("%.2f", spacexWeight * totalPassiveAum / 1_000_000_000)} Billion")
    println("----------------------------------\n")

    val results = mutableMapOf<String, Double>()

    for (company in marketData) {
        // 1. Company's weight in the index
        val companyWeight = company.marketCap.toDouble() / newIndexTotalMarketCap
        
        // 2. Dollar amount that index funds must sell of this specific company
        val forcedSellDollarVolume = companyWeight * spacexWeight * totalPassiveAum
        
        // 3. Raw Impact Ratio: Forced Selling / Daily Dollar Volume
        val impactRatio = forcedSellDollarVolume / company.dailyDollarVolume
        
        // 4. Expected Drop (Heuristic: 10% drop per 1.0 ratio, adjusted by pre-positioning)
        // If ratio is 0.5 (forced selling is 50% of daily volume), drop is ~5%
        // We multiply by (1.0 - prePositioningFactor) because hedge funds already caused part of the dip
        val expectedDropPct = (impactRatio * 10.0) * (1.0 - prePositioningFactor)
        
        results[company.ticker] = expectedDropPct
    }

    // Sort by largest drop
    val sortedResults = results.toList().sortedByDescending { (_, value) -> value }.toMap()

    println(String.format("%-8s | %-15s | %-15s | %-15s", "Ticker", "Forced Sell (\$M)", "Daily Vol (\$M)", "Expected Drop"))
    println("-".repeat(60))
    for ((ticker, drop) in sortedResults) {
        val company = marketData.find { it.ticker == ticker }!!
        val companyWeight = company.marketCap.toDouble() / newIndexTotalMarketCap
        val forcedSellM = (companyWeight * spacexWeight * totalPassiveAum) / 1_000_000
        val dailyVolM = company.dailyDollarVolume / 1_000_000
        
        println(String.format("%-8s | %-15.1f | %-15.1f | -%.2f%%", ticker, forcedSellM, dailyVolM, drop))
    }
}
